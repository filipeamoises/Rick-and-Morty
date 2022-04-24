package com.moises.rickymorty.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moises.rickymorty.domain.model.Episode

class EpisodesRecycleViewAdapter : PagingDataAdapter<Episode, EpisodesRecycleViewAdapter.EpisodesViewHolder>(COMPARATOR) {

    inner class EpisodesViewHolder(val viewDataBinding: com.moises.rickymorty.databinding.EpisodesItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        val binding = com.moises.rickymorty.databinding.EpisodesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        holder.viewDataBinding.episodeItem = getItem(position)
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Episode>() {
            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean =
                oldItem.name == newItem.name

            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean =
                oldItem.id == newItem.id
        }
    }
}
