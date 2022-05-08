package com.moises.characters.di

import android.app.Application
import androidx.room.Room
import com.moises.characters.data.local.CharacterDatabase
import com.moises.characters.data.remote.service.CharacterApi
import com.moises.characters.presentation.adapter.CharactersRecycleViewAdapter
import com.moises.common.network.buildRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CharacterModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): CharacterDatabase {
        return Room.databaseBuilder(
            application, CharacterDatabase::class.java,
            "character.db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideCharacterApi(): CharacterApi {
        return buildRetrofit().create()
    }

    //It is not necessary create a adapter injection by the complexity of this project and the adapter use
    // but it was created just for showing knowledge proposal
    @Provides
    @Singleton
    fun provideCharactersAdapter(): CharactersRecycleViewAdapter = CharactersRecycleViewAdapter()
}