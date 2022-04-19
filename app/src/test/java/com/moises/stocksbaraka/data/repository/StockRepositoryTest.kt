package com.moises.stocksbaraka.data.repository

import android.content.Context
import android.os.Build.VERSION_CODES.Q
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.moises.stocksbaraka.data.csv.StockParser
import com.moises.stocksbaraka.data.local.StockDao
import com.moises.stocksbaraka.data.local.StockDatabase
import com.moises.stocksbaraka.data.mapper.toStock
import com.moises.stocksbaraka.data.remote.StockApi
import com.moises.stocksbaraka.domain.repository.StockRepository
import com.moises.stocksbaraka.helper.TestHelper
import com.moises.stocksbaraka.provider.TestDispatcherProvider
import com.moises.stocksbaraka.util.DispatcherProvider
import com.moises.stocksbaraka.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
class StockRepositoryTest {
    private val dispatcherProvider: DispatcherProvider = TestDispatcherProvider()
    private val api: StockApi = mockk()
    private lateinit var db: StockDatabase
    private lateinit var stockDao: StockDao
    private val stockParser: StockParser = StockParser(dispatcherProvider)

    private lateinit var subject: StockRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, StockDatabase::class.java
        ).build()
        stockDao = db.stockDao
        subject = StockRepositoryImpl(dispatcherProvider, api, db, stockParser)

    }

    @Ignore
    @Test
    fun `test getStocks forcing fetch from remote, expect api call and cache update`(): Unit = runBlocking {
        val expectedStocks = TestHelper.getMockListStocks()

        coEvery { api.getStocks() } returns "".toResponseBody("csv".toMediaTypeOrNull())
        coEvery { stockParser.parse(any()) } returns expectedStocks

        val result = subject.getStocks(forceFetchFromRemote = true)

        assert(result.first().javaClass == Resource.Loading::class.java)
        assert(result.last().javaClass == Resource.Success::class.java)

        //Verify all elements from list of stocks and database data
        val listCached = db.stockDao.listStocks()
        var ct = 0
        result.last().data!!.forEach {
            assert(expectedStocks[ct] == it)
            assert(listCached[ct].toStock() == it)
            ct++
        }
    }

    @Test
    fun `test getStocks not forcing fetch from remote, expect api call and cache update`(): Unit = runBlocking {
        val expectedStocks = TestHelper.getMockListStocksEntity()

        stockDao.insertStockList(expectedStocks)

        val result = subject.getStocks(forceFetchFromRemote = false)

        assert(result.first().javaClass == Resource.Loading::class.java)
        assert(result.last().javaClass == Resource.Success::class.java)

        val resultList = result.last().data!!

        //Sort List to verify their elements
        resultList.sortedBy { it.price }
        expectedStocks.sortedBy { it.price }

        var ct = expectedStocks.lastIndex
        resultList.forEach {
            assert(expectedStocks[ct].toStock().price == it.price)
            assert(expectedStocks[ct].toStock().symbol == it.symbol)

            ct--
        }
    }

    @Test
    fun `test getRandomPriceStock not forcing fetch from remote, expect api call and cache update`(): Unit = runBlocking {
        val expectedStock = TestHelper.getMockStockEntity()

        stockDao.insertStock(expectedStock)

        val result = subject.getRandomPriceStock(forceFetchFromRemote = false, expectedStock.symbol)

        assert(result.first().javaClass == Resource.Loading::class.java)
        assert(result.last().javaClass == Resource.Success::class.java)

        assert(result.last().data!!.price == expectedStock.price)
        assert(result.last().data!!.symbol == expectedStock.symbol)

    }


}