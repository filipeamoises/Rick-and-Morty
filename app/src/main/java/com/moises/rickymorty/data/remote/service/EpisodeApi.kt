package com.moises.rickymorty.data.remote.service

import com.moises.rickymorty.data.remote.dto.EpisodesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodeApi {

    @GET("/api/episode")
    suspend fun getEpisodes(@Query("page") page: Int = 1) : Response<EpisodesDto>

}