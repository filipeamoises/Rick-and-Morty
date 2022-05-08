package com.moises.characters.di

import com.moises.characters.data.repository.CharacterRepositoryImpl
import com.moises.characters.domain.CharacterRepository
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


}