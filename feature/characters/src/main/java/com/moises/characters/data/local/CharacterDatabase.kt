package com.moises.characters.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CharacterEntity::class, CharacterRemoteKeyEntity::class],
    version = 1
)
abstract class CharacterDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
    abstract val characterRemoteKeyDao: CharacterRemoteKeyDao
}