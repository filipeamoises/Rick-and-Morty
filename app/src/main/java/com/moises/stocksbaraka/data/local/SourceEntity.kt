package com.moises.stocksbaraka.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SourceEntity(
    @PrimaryKey
    val id: Int? = null,
    val remoteId: String,
    val name: String
)