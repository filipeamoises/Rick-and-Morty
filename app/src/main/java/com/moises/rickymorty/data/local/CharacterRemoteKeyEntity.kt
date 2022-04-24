package com.moises.rickymorty.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterRemoteKeyEntity(
    @PrimaryKey val characterId: Int? = null,
    val prevKey: Int?,
    val nextKey: Int?
)
