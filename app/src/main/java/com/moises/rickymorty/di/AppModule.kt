package com.moises.rickymorty.di

import android.app.Application
import androidx.room.Room
import com.moises.rickymorty.data.local.RickyMortyDatabase
import com.moises.rickymorty.data.remote.buildRetrofit
import com.moises.rickymorty.data.remote.service.CharacterApi
import com.moises.rickymorty.data.remote.service.EpisodeApi
import com.moises.rickymorty.presentation.adapter.CharactersRecycleViewAdapter
import com.moises.rickymorty.presentation.adapter.EpisodesRecycleViewAdapter
import com.moises.rickymorty.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.create
import javax.inject.Singleton


// This app has only one activity because it and to make it simpler all dependencies configuration will be attached to the application.
// If had more activities I could create a module for a specific activity
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCharacterApi(): CharacterApi {
        return buildRetrofit().create()
    }

    @Provides
    @Singleton
    fun provideEpisodesApi(): EpisodeApi {
        return buildRetrofit().create()
    }

    @Provides
    @Singleton
    fun provideDatabase(application: Application): RickyMortyDatabase {
        return Room.databaseBuilder(
            application, RickyMortyDatabase::class.java,
            "rickymorty.db"
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
    fun provideCharactersAdapter(): CharactersRecycleViewAdapter = CharactersRecycleViewAdapter()

    @Provides
    @Singleton
    fun provideEpisodesAdapter(): EpisodesRecycleViewAdapter = EpisodesRecycleViewAdapter()

}