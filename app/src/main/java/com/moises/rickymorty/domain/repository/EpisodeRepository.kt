package com.moises.rickymorty.domain.repository

import androidx.paging.PagingData
import com.moises.rickymorty.domain.model.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun getPagedEpisodes(): Flow<PagingData<Episode>>

}