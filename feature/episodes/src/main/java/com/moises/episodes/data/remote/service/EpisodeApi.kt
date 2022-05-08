package com.moises.episodes.data.remote.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodeApi {

    @GET("/api/episode")
    suspend fun getEpisodes(@Query("page") page: Int = 1) : Response<com.moises.episodes.data.remote.dto.EpisodesDto>

}