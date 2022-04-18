package com.moises.stocksbaraka.data.remote.dto


import com.google.gson.annotations.SerializedName

data class GetNewsDto(
    @SerializedName("articles")
    val articles: List<ArticleDto>? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("totalResults")
    val totalResults: Int? = null
)