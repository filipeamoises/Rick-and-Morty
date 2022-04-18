package com.moises.stocksbaraka.data.local

import androidx.room.*

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleList(articleList: List<ArticleEntity>)

    @Query("DELETE FROM articleEntity")
    suspend fun clearArticleList()

    @Transaction
    @Query("SELECT * FROM articleEntity")
    suspend fun getArticles(): List<ArticleEntity>
}