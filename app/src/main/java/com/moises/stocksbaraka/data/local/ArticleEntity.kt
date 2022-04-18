package com.moises.stocksbaraka.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity(
    @PrimaryKey val id: Int? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String? = null,
    val urlToImage: String? = null
)