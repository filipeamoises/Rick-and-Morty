package com.moises.stocksbaraka.di

import com.moises.stocksbaraka.data.csv.CSVParser
import com.moises.stocksbaraka.data.csv.StockParser
import com.moises.stocksbaraka.data.repository.NewsRepositoryImpl
import com.moises.stocksbaraka.data.repository.StockRepositoryImpl
import com.moises.stocksbaraka.domain.modal.Stock
import com.moises.stocksbaraka.domain.repository.NewsRepository
import com.moises.stocksbaraka.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStockParser(
        stockParser: StockParser
    ): CSVParser<Stock>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepository: StockRepositoryImpl
    ): StockRepository

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsRepository: NewsRepositoryImpl
    ): NewsRepository

}