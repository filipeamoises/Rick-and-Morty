package com.moises.stocksbaraka.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moises.stocksbaraka.databinding.MainNewsItemBinding
import com.moises.stocksbaraka.domain.modal.Article

class MainNewsRecycleViewAdapter : RecyclerView.Adapter<MainNewsRecycleViewAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val viewDataBinding: MainNewsItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root)

    var articleList = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = MainNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.viewDataBinding.articleItem = articleList[position]
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    fun submitList(articles: List<Article>) {
        this.articleList = articles.toMutableList()
        notifyItemRangeChanged(0, this.articleList.size)
    }
}
