package com.moises.episodes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EpisodeRemoteKeyEntity(
    @PrimaryKey val episodeId: Int? = null,
    val prevKey: Int?,
    val nextKey: Int?
)
