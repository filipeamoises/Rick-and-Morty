package com.moises.stocksbaraka.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moises.stocksbaraka.databinding.StocksItemBinding
import com.moises.stocksbaraka.domain.modal.Stock

class StocksRecycleViewAdapter : RecyclerView.Adapter<StocksRecycleViewAdapter.StocksViewHolder>() {

    inner class StocksViewHolder(val viewDataBinding: StocksItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root)

    var stockList = mutableListOf<Stock>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {
        val binding = StocksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StocksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StocksViewHolder, position: Int) {
        holder.viewDataBinding.stockItem = stockList[position]
    }

    override fun getItemCount(): Int {
        return stockList.size
    }

    fun submitList(stocks: List<Stock>) {
        this.stockList = stocks.toMutableList()
        notifyItemRangeChanged(0, this.stockList.size)
    }

    fun updateStockPrice(stock: Stock) {
        val stockIndex = this.stockList.indexOfFirst { it.symbol.equals(stock.symbol, true) }
        this.stockList[stockIndex] = stock
        notifyItemChanged(stockIndex)
    }

}
