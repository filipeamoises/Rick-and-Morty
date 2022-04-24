package com.moises.rickymorty.data.remote

import com.google.gson.GsonBuilder
import com.moises.rickymorty.BuildConfig


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun buildRetrofit(
): Retrofit {
    val httpClientBuilder = OkHttpClient.Builder()

    configureLoggingInterceptor(httpClientBuilder)

    val httpClient = httpClientBuilder.build()

    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(httpClient)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .create()
            )
        )
        .build()
}

private fun configureLoggingInterceptor(httpClientBuilder: OkHttpClient.Builder) {
    val logging = HttpLoggingInterceptor()

    if (BuildConfig.DEBUG) {
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addInterceptor(logging)
    }
}
