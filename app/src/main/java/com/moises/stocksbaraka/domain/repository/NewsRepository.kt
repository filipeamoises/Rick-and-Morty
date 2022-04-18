package com.moises.stocksbaraka.domain.repository

import com.moises.stocksbaraka.domain.modal.Article
import com.moises.stocksbaraka.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNews(forceFetchFromRemote: Boolean): Flow<Resource<List<Article>>>

}