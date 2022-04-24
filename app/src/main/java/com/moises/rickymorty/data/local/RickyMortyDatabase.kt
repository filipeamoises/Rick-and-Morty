package com.moises.rickymorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [EpisodeEntity::class, EpisodeRemoteKeyEntity::class, CharacterEntity::class, CharacterRemoteKeyEntity::class],
    version = 1
)
abstract class RickyMortyDatabase : RoomDatabase() {
    abstract val episodeDao: EpisodeDao
    abstract val episodeRemoteKeyDao: EpisodeRemoteKeyDao
    abstract val characterDao: CharacterDao
    abstract val characterRemoteKeyDao: CharacterRemoteKeyDao
}