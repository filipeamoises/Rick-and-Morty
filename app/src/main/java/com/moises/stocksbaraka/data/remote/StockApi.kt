package com.moises.stocksbaraka.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET

interface StockApi {

    @GET("/dsancov/TestData/main/stocks.csv")
    suspend fun getStocks() : ResponseBody

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/"
    }

}