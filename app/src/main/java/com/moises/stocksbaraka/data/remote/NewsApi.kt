package com.moises.stocksbaraka.data.remote

import com.moises.stocksbaraka.data.remote.dto.GetNewsDto
import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {

    @GET("/NewsAPI/everything/cnn.json")
    suspend fun getNews(): Response<GetNewsDto>

    companion object {

        const val BASE_URL = "https://saurav.tech/"

    }

}