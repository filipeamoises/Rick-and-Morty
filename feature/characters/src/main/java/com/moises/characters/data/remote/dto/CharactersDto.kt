package com.moises.characters.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CharactersDto(
    @SerializedName("info")
    val infoCharacter: InfoCharacterDto? = null,
    @SerializedName("results")
    val results: List<CharacterDto>? = null
)