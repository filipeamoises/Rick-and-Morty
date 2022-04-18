package com.moises.stocksbaraka.data.mapper

import com.moises.stocksbaraka.data.local.StockEntity
import com.moises.stocksbaraka.domain.modal.Stock

fun StockEntity.toStock(): Stock {
    return Stock(symbol = symbol, price = price)
}

fun Stock.toStockEntity(): StockEntity {
    return StockEntity(
        symbol = symbol, price = price
    )
}