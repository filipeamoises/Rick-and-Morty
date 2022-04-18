package com.moises.stocksbaraka.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StockEntity(
    @PrimaryKey val id: Int? = null,
    val symbol: String,
    val price: Double
)
