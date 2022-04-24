package com.moises.rickymorty.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.moises.rickymorty.R
import com.moises.rickymorty.databinding.FragmentHomeBinding
import com.moises.rickymorty.presentation.adapter.CharactersRecycleViewAdapter
import com.moises.rickymorty.presentation.adapter.EpisodesRecycleViewAdapter
import com.moises.rickymorty.presentation.adapter.LoadItemStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var charactersAdapter: CharactersRecycleViewAdapter

    @Inject
    lateinit var episodesAdapter: EpisodesRecycleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelData()
        setupRecycleViewCharacters()
        setupRecycleViewEpisodes()
    }

    private fun setupRecycleViewEpisodes() {
        binding.rvEpisodes.adapter = episodesAdapter.withLoadStateHeaderAndFooter(footer = LoadItemStateAdapter { episodesAdapter.retry() },
            header = LoadItemStateAdapter { episodesAdapter.retry() })

        episodesAdapter.addLoadStateListener { loadState ->
            val refreshState = loadState.source.refresh
            binding.swiperefresh.isRefreshing = refreshState is LoadState.Loading
            handleError(loadState)
        }

        //Include list divider
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rvEpisodes.addItemDecoration(dividerItemDecoration)

        //Bind Swipe to refresh
        binding.swiperefresh.setOnRefreshListener { episodesAdapter.retry() }
    }

    private fun setupRecycleViewCharacters() {
        binding.rvCharacters.adapter = charactersAdapter.withLoadStateHeaderAndFooter(footer = LoadItemStateAdapter { charactersAdapter.retry() },
            header = LoadItemStateAdapter { charactersAdapter.retry() })

        charactersAdapter.addLoadStateListener { loadState ->
            handleError(loadState)
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        if (loadState.refresh is LoadState.Error || loadState.append is LoadState.Error || loadState.prepend is LoadState.Error) {
            context?.let { Toast.makeText(it, getString(R.string.generic_error), Toast.LENGTH_SHORT).show() }
        }
    }

    private fun observeViewModelData() {
        viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getCharacters().collect {
                    charactersAdapter.submitData(it)
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getEpisodes().collect {
                        episodesAdapter.submitData(it)
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