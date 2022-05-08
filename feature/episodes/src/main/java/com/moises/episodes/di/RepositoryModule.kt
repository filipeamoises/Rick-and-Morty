package com.moises.episodes.di

import com.moises.episodes.data.repository.EpisodeRepositoryImpl
import com.moises.episodes.domain.repository.EpisodeRepository
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
    abstract fun bindEpisodeRepository(
        newsRepository: EpisodeRepositoryImpl
    ): EpisodeRepository
}