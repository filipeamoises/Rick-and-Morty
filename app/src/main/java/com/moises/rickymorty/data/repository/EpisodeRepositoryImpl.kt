package com.moises.rickymorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.moises.rickymorty.data.local.RickyMortyDatabase
import com.moises.rickymorty.data.mapper.toEpisode
import com.moises.rickymorty.data.paging.EpisodePageRemoteMediator
import com.moises.rickymorty.data.remote.service.EpisodeApi
import com.moises.rickymorty.domain.repository.EpisodeRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeRepositoryImpl @Inject constructor(
    private val api: EpisodeApi,
    private val db: RickyMortyDatabase
) : EpisodeRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedEpisodes() = Pager(
        config = PagingConfig(20),
        remoteMediator = EpisodePageRemoteMediator(db = db, episodeApi = api)
    ) {
        db.episodeDao.listEpisodes()
    }.flow.map { it.map { item -> item.toEpisode() } }
}