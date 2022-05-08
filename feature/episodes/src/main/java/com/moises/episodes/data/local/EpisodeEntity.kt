package com.moises.episodes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EpisodeEntity(
    @PrimaryKey val id: Int? = null,
    val airDate: String? = null,
    val created: String? = null,
    val episode: String? = null,
    val name: String? = null,
    val url: String? = null
)
