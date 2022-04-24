package com.moises.rickymorty.data.remote.service

import com.moises.rickymorty.data.remote.dto.CharactersDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("/api/character")
    suspend fun getCharacters(@Query("page") page: Int = 1) : Response<CharactersDto>

}