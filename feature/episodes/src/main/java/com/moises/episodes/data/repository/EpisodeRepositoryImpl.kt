package com.moises.episodes.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.moises.episodes.data.local.EpisodeDatabase
import com.moises.episodes.data.mapper.toEpisode
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeRepositoryImpl @Inject constructor(
    private val api: com.moises.episodes.data.remote.service.EpisodeApi,
    private val db: EpisodeDatabase
) : com.moises.episodes.domain.repository.EpisodeRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedEpisodes() = Pager(
        config = PagingConfig(20),
        remoteMediator = com.moises.episodes.data.paging.EpisodePageRemoteMediator(db = db, episodeApi = api)
    ) {
        db.episodeDao.listEpisodes()
    }.flow.map { it.map { item -> item.toEpisode() } }
}