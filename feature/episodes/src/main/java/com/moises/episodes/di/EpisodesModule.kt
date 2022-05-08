package com.moises.episodes.di

import android.app.Application
import androidx.room.Room
import com.moises.common.network.buildRetrofit
import com.moises.episodes.data.local.EpisodeDatabase
import com.moises.episodes.data.remote.service.EpisodeApi
import com.moises.episodes.presentation.adapter.EpisodesRecycleViewAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EpisodesModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): EpisodeDatabase {
        return Room.databaseBuilder(
            application, EpisodeDatabase::class.java,
            "episodes.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideEpisodesApi(): EpisodeApi {
        return buildRetrofit().create()
    }

    //It is not necessary create a adapter injection by the complexity of this project and the adapter use
    // but it was created just for showing knowledge proposal
    @Provides
    @Singleton
    fun provideEpisodesAdapter(): EpisodesRecycleViewAdapter = EpisodesRecycleViewAdapter()

}