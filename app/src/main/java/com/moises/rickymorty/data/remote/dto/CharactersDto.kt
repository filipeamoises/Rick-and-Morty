package com.moises.rickymorty.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CharactersDto(
    @SerializedName("info")
    val info: InfoDto? = null,
    @SerializedName("results")
    val results: List<CharacterDto>? = null
)