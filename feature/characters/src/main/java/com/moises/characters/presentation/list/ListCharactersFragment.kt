package com.moises.characters.presentation.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import com.moises.characters.R
import com.moises.characters.databinding.FragmentListCharactersBinding
import com.moises.characters.domain.model.Character
import com.moises.characters.presentation.adapter.CharactersRecycleViewAdapter
import com.moises.characters.presentation.adapter.LoadItemStateAdapter
import com.moises.characters.presentation.detail.DetailCharacterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListCharactersFragment : Fragment() {

    private var _binding: FragmentListCharactersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListCharactersViewModel by viewModels()

    lateinit var charactersAdapter: CharactersRecycleViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCharactersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleViewCharacters()
        observeViewModelData()
    }

    private fun setupRecycleViewCharacters() {
        charactersAdapter = CharactersRecycleViewAdapter { openCharacterDetail(it) }
        binding.rvCharacters.adapter = charactersAdapter.withLoadStateHeaderAndFooter(footer = LoadItemStateAdapter { charactersAdapter.retry() },
            header = LoadItemStateAdapter { charactersAdapter.retry() })

        binding.btRetryCharacters.setOnClickListener { charactersAdapter.retry() }

        charactersAdapter.addLoadStateListener { loadState ->
            val refreshState = loadState.source.refresh
            binding.llCharacterLoading.visibility = if (refreshState is LoadState.Loading) View.VISIBLE else View.INVISIBLE
            binding.progressBar.visibility = if (refreshState is LoadState.Loading) View.VISIBLE else View.GONE
            binding.btRetryCharacters.visibility = View.GONE

            if (refreshState !is LoadState.Loading)
                binding.rvCharacters.visibility = View.VISIBLE

            handleErrorCharactersList(loadState)
        }
    }

    private fun openCharacterDetail(character: Character) {
        val intent = Intent(activity, DetailCharacterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra(DetailCharacterActivity.CHARACTER_ID_EXTRA, character.id)
        activity?.startActivity(intent)
    }

    private fun handleErrorCharactersList(loadState: CombinedLoadStates) {
        if (loadState.refresh is LoadState.Error || loadState.append is LoadState.Error || loadState.prepend is LoadState.Error) {
            if (loadState.refresh is LoadState.Error) {
                binding.llCharacterLoading.visibility = View.VISIBLE
                binding.btRetryCharacters.visibility = View.VISIBLE
                binding.rvCharacters.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            }
            context?.let { Toast.makeText(it, getString(R.string.generic_error_episodes_character), Toast.LENGTH_SHORT).show() }
        }
    }

    private fun observeViewModelData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCharacters().collect {
                charactersAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadImageCircle")
        fun loadImageCircle(view: ImageView, image: String?) {
            Glide.with(view.context)
                .load(image)
                .circleCrop()
                .into(view)
        }
    }
}