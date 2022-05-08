package com.moises.characters.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey val id: Int? = null,
    val created: String? = null,
    val gender: String? = null,
    val image: String? = null,
    val name: String? = null,
    val species: String? = null,
    val status: String? = null,
    val type: String? = null,
    val page: Int? = null
)
