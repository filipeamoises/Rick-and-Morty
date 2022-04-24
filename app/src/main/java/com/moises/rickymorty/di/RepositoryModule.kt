package com.moises.rickymorty.di

import com.moises.rickymorty.data.repository.CharacterRepositoryImpl
import com.moises.rickymorty.data.repository.EpisodeRepositoryImpl
import com.moises.rickymorty.domain.repository.CharacterRepository
import com.moises.rickymorty.domain.repository.EpisodeRepository
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
    abstract fun bindCharacterRepository(
        characterRepository: CharacterRepositoryImpl
    ): CharacterRepository

    @Binds
    @Singleton
    abstract fun bindEpisodeRepository(
        newsRepository: EpisodeRepositoryImpl
    ): EpisodeRepository

}