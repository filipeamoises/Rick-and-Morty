package com.moises.episodes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [EpisodeEntity::class, EpisodeRemoteKeyEntity::class],
    version = 1
)
abstract class EpisodeDatabase : RoomDatabase() {
    abstract val episodeDao: EpisodeDao
    abstract val episodeRemoteKeyDao: EpisodeRemoteKeyDao
}