package com.moises.stocksbaraka.data.repository

import com.moises.stocksbaraka.data.local.ArticleDao
import com.moises.stocksbaraka.data.local.StockDatabase
import com.moises.stocksbaraka.data.mapper.toArticleEntityList
import com.moises.stocksbaraka.data.mapper.toArticleList
import com.moises.stocksbaraka.data.remote.NewsApi
import com.moises.stocksbaraka.domain.modal.Article
import com.moises.stocksbaraka.domain.repository.NewsRepository
import com.moises.stocksbaraka.util.DispatcherProvider
import com.moises.stocksbaraka.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val api: NewsApi,
    db: StockDatabase
) : NewsRepository {

    private var dao: ArticleDao = db.articleDao

    override suspend fun getNews(forceFetchFromRemote: Boolean): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading())

            //Get data from local database and Transform the data from ArticleEntity to Article Domain model
            try {
                var cachedArticles: List<Article> = emptyList()
                if (!forceFetchFromRemote) {
                    withContext(dispatcherProvider.io) {
                        cachedArticles = dao.getArticles().toArticleList()
                    }
                }
                if (forceFetchFromRemote || cachedArticles.isEmpty()) {
                    val responseArticle = try {
                        withContext(dispatcherProvider.io) {
                            api.getNews()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        emit(Resource.Error(message = "Oops... Some error happened. We could not load the data."))
                        null
                    } catch (e: HttpException) {
                        e.printStackTrace()
                        emit(Resource.Error(message = "Oops... Some error happened. We could not load the data. Please try again later."))
                        null
                    }

                    if (responseArticle == null || !responseArticle.isSuccessful) {
                        emit(Resource.Error(message = "Oops... Some error happened. We could not load the data. Please try again later."))
                        return@flow
                    }

                    responseArticle.body().let { articleRemote ->
                        //Update the local cache
                        withContext(dispatcherProvider.io) {
                            dao.clearArticleList()
                            articleRemote?.toArticleEntityList()?.let { dao.insertArticleList(it) }
                        }
                        //Emit the update data from remote
                        emit(Resource.Success(data = articleRemote?.toArticleList()))
                    }

                } else {
                    emit(Resource.Success(data = cachedArticles))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Oops... Some error happened. We could not load the data."))
            }
        }
    }

}