package com.moises.stocksbaraka.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockList(stockList: List<StockEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stockList: StockEntity)

    @Query("DELETE FROM stockEntity")
    suspend fun clearStockList()

    @Query("SELECT * FROM stockEntity WHERE UPPER(symbol) LIKE '%' || UPPER(:query) || '%' OR UPPER(:query) == symbol")
    suspend fun searchStock(query: String): List<StockEntity>

    @Query("SELECT * FROM stockEntity")
    suspend fun listStocks(): List<StockEntity>
}