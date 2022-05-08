package com.moises.characters.data.remote.service

import com.moises.characters.data.remote.dto.CharactersDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("/api/character")
    suspend fun getCharacters(@Query("page") page: Int = 1) : Response<CharactersDto>

}