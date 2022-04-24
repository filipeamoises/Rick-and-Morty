package com.moises.rickymorty.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodeList(characterList: List<EpisodeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(characterList: EpisodeEntity)

    @Query("DELETE FROM episodeEntity")
    suspend fun clearEpisodeList()

    @Query("SELECT * FROM episodeEntity")
    fun listEpisodes(): PagingSource<Int, EpisodeEntity>

}