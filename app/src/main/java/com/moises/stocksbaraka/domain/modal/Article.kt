package com.moises.stocksbaraka.domain.modal

import java.util.*

data class Article(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: Date,
    val title: String,
    val url: String? = null,
    val urlToImage: String? = null
)
