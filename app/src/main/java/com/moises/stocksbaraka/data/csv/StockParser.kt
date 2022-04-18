package com.moises.stocksbaraka.data.csv

import com.moises.stocksbaraka.domain.modal.Stock
import com.moises.stocksbaraka.util.DispatcherProvider
import com.opencsv.CSVReader
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class StockParser @Inject constructor(private val dispatcherProvider: DispatcherProvider) : CSVParser<Stock> {
    override suspend fun parse(stream: InputStream): List<Stock> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(dispatcherProvider.io) {
            csvReader.readAll().drop(1).mapNotNull {
                Stock(symbol = it.getOrNull(0) ?: return@mapNotNull null, price = (it.getOrNull(1) ?: "0").toDouble())
            }.also {
                csvReader.close()
            }
        }
    }
}