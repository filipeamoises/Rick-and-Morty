package com.moises.stocksbaraka.presentation.stocks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moises.stocksbaraka.domain.modal.Article
import com.moises.stocksbaraka.domain.modal.Stock
import com.moises.stocksbaraka.domain.repository.NewsRepository
import com.moises.stocksbaraka.domain.repository.StockRepository
import com.moises.stocksbaraka.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksViewModel @Inject constructor(private val stockRepository: StockRepository, private val newsRepository: NewsRepository) : ViewModel() {

    sealed class StocksViewState {
        class Success(val stocks: List<Stock>) : StocksViewState()
        class Failure(val errorMessage: String) : StocksViewState()
        object Loading : StocksViewState()
        object Empty : StocksViewState()
    }

    sealed class SingleStockViewState {
        class Success(val stock: Stock) : SingleStockViewState()
        class Failure(val errorMessage: String) : SingleStockViewState()
        object Loading : SingleStockViewState()
        object Empty : SingleStockViewState()
    }

    sealed class NewsViewState {
        class Success(val articles: List<Article>) : NewsViewState()
        class Failure(val errorMessage: String) : NewsViewState()
        object Loading : NewsViewState()
        object Empty : NewsViewState()
    }

    private val _stocksState = MutableStateFlow<StocksViewState>(StocksViewState.Empty)
    val stocksState: StateFlow<StocksViewState> = _stocksState

    private val _singleStockState = MutableStateFlow<SingleStockViewState>(SingleStockViewState.Empty)
    val singleStockState: StateFlow<SingleStockViewState> = _singleStockState

    private val _newsState = MutableStateFlow<NewsViewState>(NewsViewState.Empty)
    val newsState: StateFlow<NewsViewState> = _newsState

    init {
        fetchStocks(false)
        fetchNews(false)
    }

    fun getStockPrice(symbol: String) {
        viewModelScope.launch {
            stockRepository.getRandomPriceStock(false, symbol).collect { result ->
                _singleStockState.value = when (result) {
                    is Resource.Success -> {
                        SingleStockViewState.Success(stock = result.data ?: Stock("", 0.0))
                    }
                    is Resource.Error -> {
                        SingleStockViewState.Failure(errorMessage = result.message ?: "Generic Error Message")
                    }
                    is Resource.Loading -> {
                        SingleStockViewState.Loading
                    }
                }
            }
        }
    }

    fun fetchStocks(forceFetchRemote: Boolean) {
        viewModelScope.launch {
            stockRepository.getStocks(forceFetchRemote).collect { result ->
                _stocksState.value = when (result) {
                    is Resource.Success -> {
                        StocksViewState.Success(stocks = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        StocksViewState.Failure(errorMessage = result.message ?: "Generic Error Message")
                    }
                    is Resource.Loading -> {
                        StocksViewState.Loading
                    }
                }
            }
        }
    }

    fun fetchNews(forceFetchRemote: Boolean) {
        viewModelScope.launch {
            newsRepository.getNews(forceFetchRemote).collect { result ->
                _newsState.value = when (result) {
                    is Resource.Success -> {
                        NewsViewState.Success(articles = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        NewsViewState.Failure(errorMessage = result.message ?: "Generic Error Message")
                    }
                    is Resource.Loading -> {
                        NewsViewState.Loading
                    }
                }
            }
        }
    }
}