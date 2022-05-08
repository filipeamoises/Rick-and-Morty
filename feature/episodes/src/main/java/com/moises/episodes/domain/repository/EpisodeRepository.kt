package com.moises.episodes.domain.repository

import androidx.paging.PagingData
import com.moises.episodes.domain.model.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun getPagedEpisodes(): Flow<PagingData<Episode>>

}