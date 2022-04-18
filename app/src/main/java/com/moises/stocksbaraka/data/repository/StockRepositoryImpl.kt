package com.moises.stocksbaraka.data.repository

import com.moises.stocksbaraka.data.csv.CSVParser
import com.moises.stocksbaraka.data.local.StockDao
import com.moises.stocksbaraka.data.local.StockDatabase
import com.moises.stocksbaraka.data.mapper.toStock
import com.moises.stocksbaraka.data.mapper.toStockEntity
import com.moises.stocksbaraka.data.remote.StockApi
import com.moises.stocksbaraka.domain.modal.Stock
import com.moises.stocksbaraka.domain.repository.StockRepository
import com.moises.stocksbaraka.util.DispatcherProvider
import com.moises.stocksbaraka.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val api: StockApi,
    db: StockDatabase,
    private val stockParser: CSVParser<Stock>
) : StockRepository {

    private var dao: StockDao = db.stockDao

    private suspend fun getRemoteStocks(): Flow<Resource<List<Stock>>> {
        return flow {
            val remoteStocks = try {
                withContext(dispatcherProvider.io) {
                    val response = api.getStocks()
                    stockParser.parse(response.byteStream())
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Oops... Some error happened. We could not load the data."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Oops... Some error happened. We could not load the data. Please try again later."))
                null
            }
            remoteStocks?.let { list ->
                withContext(dispatcherProvider.io) {
                    //Update the local cache
                    dao.clearStockList()
                    dao.insertStockList(list.map { it.toStockEntity() })
                }
                emit(Resource.Success(data = list))
            }
        }
    }

    override suspend fun getStocks(forceFetchFromRemote: Boolean): Flow<Resource<List<Stock>>> {
        return flow {
            emit(Resource.Loading())

            //Get data from local database and Transform the data from StockEntity to Stock Domain model
            try {
                var cachedStocks: List<Stock>

                withContext(dispatcherProvider.io) {
                    cachedStocks = dao.listStocks().map { it.toStock() }
                }
                if (cachedStocks.isEmpty() || forceFetchFromRemote) {
                    getRemoteStocks().collect { remoteStocks ->
                        when (remoteStocks) {
                            is Resource.Success -> {
                                emit(Resource.Success(data = remoteStocks.data?.distinctBy { it.symbol }))
                            }
                            else -> {
                                emit(remoteStocks)
                            }
                        }
                    }
                } else {
                    emit(Resource.Success(data = cachedStocks.distinctBy { it.symbol }))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Oops... Some error happened. We could not load the data."))
            }
        }
    }

    override suspend fun getRandomPriceStock(forceFetchFromRemote: Boolean, symbol: String): Flow<Resource<Stock>> {
        return flow {
            emit(Resource.Loading())

            //Get data from local database and Transform the data from StockEntity to Stock Domain model
            try {
                var cachedStocks: List<Stock>

                withContext(dispatcherProvider.io) {
                    cachedStocks = dao.searchStock(symbol).map { it.toStock() }
                }
                if (cachedStocks.isEmpty() || forceFetchFromRemote) {
                    getRemoteStocks().collect { remoteStocks ->
                        when (remoteStocks) {
                            is Resource.Success -> {
                                emit(Resource.Success(data = remoteStocks.data?.filter { it.symbol.uppercase() == symbol.uppercase() }?.random()))
                            }
                            is Resource.Error -> {
                                emit(Resource.Error(message = "Oops... Some error happened. We could not load the data. Please try again later."))
                            }
                            else -> {}
                        }
                    }

                } else {
                    emit(Resource.Success(data = cachedStocks.random()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Oops... Some error happened. We could not load the data."))
            }
        }
    }
}