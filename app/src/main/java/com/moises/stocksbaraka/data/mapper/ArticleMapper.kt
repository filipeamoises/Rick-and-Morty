package com.moises.stocksbaraka.data.mapper

import com.moises.stocksbaraka.data.local.ArticleEntity
import com.moises.stocksbaraka.data.remote.dto.ArticleDto
import com.moises.stocksbaraka.data.remote.dto.GetNewsDto
import com.moises.stocksbaraka.domain.modal.Article
import com.moises.stocksbaraka.util.extension.toLocalDate
import java.util.*


fun ArticleDto.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        author = author ?: "",
        content = content ?: "",
        description = description ?: "",
        publishedAt = publishedAt ?: "",
        title = title ?: "",
        url = url,
        urlToImage = urlToImage
    )
}

fun ArticleDto.toArticle(): Article {

    return Article(
        author = author,
        content = content ?: "",
        description = description ?: "",
        publishedAt = publishedAt?.toLocalDate() ?: Date(),
        title = title ?: "",
        url = url,
        urlToImage = urlToImage
    )
}

fun GetNewsDto.toArticleList(): List<Article> {
    return this.articles?.map { it.toArticle() } ?: emptyList()
}

fun GetNewsDto.toArticleEntityList(): List<ArticleEntity> {
    return this.articles?.map { it.toArticleEntity() } ?: emptyList()
}

fun List<ArticleEntity>?.toArticleList(): List<Article> {
    return this?.map {
        Article(
            author = it.author,
            content = it.content,
            description = it.description,
            publishedAt = it.publishedAt.toLocalDate(),
            title = it.title,
            url = it.url,
            urlToImage = it.urlToImage
        )
    } ?: emptyList()

}