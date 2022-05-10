package com.moises.episodes.data.paging

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.moises.episodes.data.local.*
import com.moises.episodes.data.mapper.toEpisodeEntity
import com.moises.episodes.data.remote.service.EpisodeApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class EpisodePageRemoteMediator(
    private val db: EpisodeDatabase,
    private val episodeApi: EpisodeApi
) : RemoteMediator<Int, EpisodeEntity>() {

    private val episodeDao: EpisodeDao = db.episodeDao
    private val episodeRemoteKeyDao: EpisodeRemoteKeyDao = db.episodeRemoteKeyDao

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, EpisodeEntity>): EpisodeRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id.let { cachedId ->
                episodeRemoteKeyDao.remoteKeysEpisodeId(cachedId ?: 0)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, EpisodeEntity>): EpisodeRemoteKeyEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { it -> it.id?.let { it1 -> episodeRemoteKeyDao.remoteKeysEpisodeId(it1) } }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, EpisodeEntity>): EpisodeRemoteKeyEntity? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { it -> it.id?.let { it1 -> episodeRemoteKeyDao.remoteKeysEpisodeId(it1) } }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodeEntity>
    ): MediatorResult {
        try {
            var pageKey = 1
            when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    pageKey = remoteKey?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getFirstRemoteKey(state)
                    val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                        endOfPaginationReached = false
                    )
                    pageKey = prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getLastRemoteKey(state)
                    pageKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = false)

                }
            }

            val response = episodeApi.getEpisodes(page = pageKey)
            if (response.isSuccessful) {
                response.body()?.results?.let {
                    db.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            episodeDao.clearEpisodeList()
                            episodeRemoteKeyDao.clearTable()
                        }
                        var nextPageNumber: Int? = null
                        if (response.body()?.info?.next != null) {
                            val uri = Uri.parse(response.body()?.info?.next!!)
                            val nextPageQuery = uri.getQueryParameter("page")
                            nextPageNumber = nextPageQuery?.toInt()
                        }
                        var prevPageNumber: Int? = null
                        if (response.body()?.info?.prev != null) {
                            val uri = Uri.parse(response.body()?.info?.prev!!)
                            val prevPageQuery = uri.getQueryParameter("page")

                            prevPageNumber = prevPageQuery?.toInt()
                        }

                        val keys = response.body()?.results?.map {
                            EpisodeRemoteKeyEntity(it.id, prevKey = prevPageNumber, nextKey = nextPageNumber)
                        }
                        if (keys != null) {
                            episodeRemoteKeyDao.insertAll(keys)
                        }
                        episodeDao.insertEpisodeList(it.map { item -> item.toEpisodeEntity() })
                    }
                }
                return MediatorResult.Success(endOfPaginationReached = response.body()?.info?.next == null)
            } else {
                return MediatorResult.Error(Exception(response.errorBody().toString()))
            }

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}