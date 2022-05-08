package com.moises.characters.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moises.characters.databinding.CharacterItemBinding

class CharactersRecycleViewAdapter : PagingDataAdapter<com.moises.characters.domain.Character, CharactersRecycleViewAdapter.CharactersViewHolder>(COMPARATOR) {

    inner class CharactersViewHolder(val viewDataBinding: CharacterItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.viewDataBinding.characterItem = getItem(position)
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<com.moises.characters.domain.Character>() {
            override fun areContentsTheSame(oldItem: com.moises.characters.domain.Character, newItem: com.moises.characters.domain.Character): Boolean =
                oldItem.name == newItem.name

            override fun areItemsTheSame(oldItem: com.moises.characters.domain.Character, newItem: com.moises.characters.domain.Character): Boolean =
                oldItem.id == newItem.id
        }
    }
}
