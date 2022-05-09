package com.moises.characters.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moises.characters.databinding.CharacterItemBinding
import com.moises.characters.domain.model.Character

class CharactersRecycleViewAdapter(private var clickItem: ((Character) -> Unit)) : PagingDataAdapter<Character, CharactersRecycleViewAdapter.CharactersViewHolder>(COMPARATOR) {

    inner class CharactersViewHolder(val viewDataBinding: CharacterItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.viewDataBinding.characterItem = getItem(position)
        holder.viewDataBinding.root.setOnClickListener { getItem(position)?.let { character -> clickItem(character) } }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.name == newItem.name

            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.id == newItem.id
        }
    }
}
