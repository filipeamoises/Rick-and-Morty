package com.moises.stocksbaraka.domain.repository

import com.moises.stocksbaraka.domain.modal.Stock
import com.moises.stocksbaraka.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getStocks(forceFetchFromRemote: Boolean): Flow<Resource<List<Stock>>>

    suspend fun getRandomPriceStock(forceFetchFromRemote: Boolean, symbol: String): Flow<Resource<Stock>>

}