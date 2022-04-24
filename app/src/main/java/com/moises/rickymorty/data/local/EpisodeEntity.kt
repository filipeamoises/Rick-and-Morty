package com.moises.rickymorty.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class EpisodeEntity(
    @PrimaryKey val id: Int? = null,
    val airDate: String? = null,
    val created: String? = null,
    val episode: String? = null,
    val name: String? = null,
    val url: String? = null
)
