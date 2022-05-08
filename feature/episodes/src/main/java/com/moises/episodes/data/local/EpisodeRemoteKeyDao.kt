package com.moises.episodes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EpisodeRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<EpisodeRemoteKeyEntity>)

    @Query("SELECT * FROM episodeRemoteKeyEntity WHERE episodeId = :id")
    suspend fun remoteKeysEpisodeId(id: Int): EpisodeRemoteKeyEntity?

    @Query("DELETE FROM episodeRemoteKeyEntity")
    suspend fun clearTable()
}