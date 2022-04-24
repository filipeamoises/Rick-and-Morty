package com.moises.rickymorty.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<CharacterRemoteKeyEntity>)

    @Query("SELECT * FROM characterRemoteKeyEntity WHERE characterId = :id")
    suspend fun remoteKeysCharacterId(id: Int): CharacterRemoteKeyEntity?

    @Query("DELETE FROM characterRemoteKeyEntity")
    suspend fun clearTable()
}