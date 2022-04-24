package com.moises.rickymorty.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moises.rickymorty.R

class LoadItemStateAdapter(private val retryAction: () -> Unit) : LoadStateAdapter<LoadItemStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(val viewDataBinding: com.moises.rickymorty.databinding.LoadStateItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onBindViewHolder(holder: LoadItemStateAdapter.LoadStateViewHolder, loadState: LoadState) {
        holder.viewDataBinding.progressBar.isVisible = loadState is LoadState.Loading
        holder.viewDataBinding.retryButton.isVisible = loadState !is LoadState.Loading
        holder.viewDataBinding.retryButton.setOnClickListener { retryAction.invoke() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadItemStateAdapter.LoadStateViewHolder {
        val binding = com.moises.rickymorty.databinding.LoadStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }
}
