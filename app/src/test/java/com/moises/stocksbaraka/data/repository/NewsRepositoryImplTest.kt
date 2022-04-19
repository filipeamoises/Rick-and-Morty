package com.moises.stocksbaraka.data.repository

import android.content.Context
import android.os.Build.VERSION_CODES.Q
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.moises.stocksbaraka.data.local.ArticleDao
import com.moises.stocksbaraka.data.local.StockDatabase
import com.moises.stocksbaraka.data.mapper.toArticle
import com.moises.stocksbaraka.data.remote.NewsApi
import com.moises.stocksbaraka.domain.repository.NewsRepository
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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
class NewsRepositoryImplTest {
    private val dispatcherProvider: DispatcherProvider = TestDispatcherProvider()
    private val api: NewsApi = mockk<NewsApi>()
    private lateinit var db: StockDatabase
    private lateinit var articleDao: ArticleDao

    private lateinit var subject: NewsRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, StockDatabase::class.java
        ).build()
        articleDao = db.articleDao
        subject = NewsRepositoryImpl(dispatcherProvider, api, db)

    }


    @Test
    fun `test getNews forcing fetch from remote, expect api call and cache update`(): Unit = runBlocking {
        coEvery { api.getNews() } returns Response.success(TestHelper.getMockGetNewsDto())
        val expectedArticle = TestHelper.getMockArticleDto().toArticle()
        val result = subject.getNews(forceFetchFromRemote = true)
        assert(result.first().javaClass == Resource.Loading::class.java)
        assert(result.last().javaClass == Resource.Success::class.java)
        assert(result.last().data!!.first() == expectedArticle)
    }

    @Test
    fun `test getNews not forcing fetch from remote with filled DB, expect uses cache`(): Unit = runBlocking {

        val expectedArticle = TestHelper.getMockArticleDto().toArticle()

        //Insert on database
        db.articleDao.insertArticleList(TestHelper.getMockListArticleEntity())

        val result = subject.getNews(forceFetchFromRemote = false)

        assert(result.first().javaClass == Resource.Loading::class.java)
        assert(result.last().javaClass == Resource.Success::class.java)
        assert(result.last().data!!.first().author == expectedArticle.author)
        assert(result.last().data!!.first().description == expectedArticle.description)
        assert(result.last().data!!.first().url == expectedArticle.url)
        assert(result.last().data!!.first().urlToImage == expectedArticle.urlToImage)
        assert(result.last().data!!.first().content == expectedArticle.content)
        assert(result.last().data!!.first().title == expectedArticle.title)
        assert(result.last().data!!.first().publishedAt == expectedArticle.publishedAt)
    }

}