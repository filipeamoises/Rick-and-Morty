package com.moises.stocksbaraka.di

import android.app.Application
import androidx.room.Room
import com.moises.stocksbaraka.data.local.StockDatabase
import com.moises.stocksbaraka.data.remote.NewsApi
import com.moises.stocksbaraka.data.remote.StockApi
import com.moises.stocksbaraka.presentation.adapter.MainNewsRecycleViewAdapter
import com.moises.stocksbaraka.presentation.adapter.NewsRecycleViewAdapter
import com.moises.stocksbaraka.presentation.adapter.StocksRecycleViewAdapter
import com.moises.stocksbaraka.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


// This app has only one activity because it and to make it simpler all dependencies configuration will be attached to the application.
// If had more activities I could create a module for a specific activity
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockApi {
        return Retrofit.Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create()
    }

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(application: Application): StockDatabase {
        return Room.databaseBuilder(
            application, StockDatabase::class.java,
            "stocks.db"
        ).build()
    }


    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
    }


    //It is not necessary create a adapter injection by the complexity of this project and the adapter use
    // but it was created just for showing knowledge proposal
    @Provides
    @Singleton
    fun provideStocksAdapter(): StocksRecycleViewAdapter = StocksRecycleViewAdapter()

    @Provides
    @Singleton
    fun provideMainNewsAdapter(): MainNewsRecycleViewAdapter = MainNewsRecycleViewAdapter()

    @Provides
    @Singleton
    fun provideNewsAdapter(): NewsRecycleViewAdapter = NewsRecycleViewAdapter()

}