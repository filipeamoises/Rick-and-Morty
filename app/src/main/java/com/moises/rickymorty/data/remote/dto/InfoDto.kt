package com.moises.rickymorty.data.remote.dto

import com.google.gson.annotations.SerializedName

data class InfoDto(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("pages")
    val pages: Int? = null,
    @SerializedName("prev")
    val prev: String? = null
)