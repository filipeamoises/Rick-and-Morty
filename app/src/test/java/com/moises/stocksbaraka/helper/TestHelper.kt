package com.moises.stocksbaraka.helper

import com.moises.stocksbaraka.data.local.ArticleEntity
import com.moises.stocksbaraka.data.local.StockEntity
import com.moises.stocksbaraka.data.mapper.toArticleEntity
import com.moises.stocksbaraka.data.mapper.toStock
import com.moises.stocksbaraka.data.remote.dto.ArticleDto
import com.moises.stocksbaraka.data.remote.dto.GetNewsDto
import com.moises.stocksbaraka.domain.modal.Stock
import kotlin.random.Random

class TestHelper {

    companion object {
        fun getMockArticleDto(): ArticleDto {
            return ArticleDto(
                author = "Michael Jackson",
                description = "Michael Jackson is alive",
                title = "Where is Michael?",
                urlToImage = "https://www.google.com",
                url = "https://www.google.com",
                publishedAt = "2000-10-31T01:30:00.000Z"
            )
        }

        fun getMockGetNewsDto(): GetNewsDto {
            return GetNewsDto(
                status = "success",
                totalResults = 3,
                articles = listOf(
                    getMockArticleDto(),
                    getMockArticleDto(),
                    getMockArticleDto()
                )
            )
        }

        fun getMockListArticleEntity(): List<ArticleEntity> {
            return getMockGetNewsDto().articles!!.map { it.toArticleEntity() }
        }


        fun getMockStockEntity(): StockEntity {
            return StockEntity(id = 0, "MIC".random().toString(), Random.nextDouble(-99.0, 99.0))
        }

        fun getMockListStocksEntity(): List<StockEntity> {
            return listOf(getMockStockEntity(), getMockStockEntity(), getMockStockEntity(), getMockStockEntity())
        }

        fun getMockListStocks(): List<Stock> {
            return getMockListStocksEntity().map { it.toStock() }
        }

    }
}