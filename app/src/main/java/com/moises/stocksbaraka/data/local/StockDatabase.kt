package com.moises.stocksbaraka.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ArticleEntity::class, StockEntity::class],
    version = 2
)
abstract class StockDatabase : RoomDatabase() {
    abstract val articleDao: ArticleDao
    abstract val stockDao: StockDao
}