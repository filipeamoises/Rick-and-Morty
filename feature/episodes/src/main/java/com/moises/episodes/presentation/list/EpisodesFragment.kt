package com.moises.episodes.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.moises.episodes.R
import com.moises.episodes.databinding.FragmentListEpisodesBinding
import com.moises.episodes.presentation.adapter.LoadItemStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class EpisodesFragment : Fragment() {

    private var _binding: FragmentListEpisodesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EpisodesViewModel by viewModels()

    @Inject
    lateinit var episodesAdapter: com.moises.episodes.presentation.adapter.EpisodesRecycleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListEpisodesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelData()
        setupRecycleViewEpisodes()
    }

    private fun setupRecycleViewEpisodes() {
        binding.rvEpisodes.adapter = episodesAdapter.withLoadStateHeaderAndFooter(footer = LoadItemStateAdapter { episodesAdapter.retry() },
            header = LoadItemStateAdapter { episodesAdapter.retry() })

        episodesAdapter.addLoadStateListener { loadState ->
            val refreshState = loadState.source.refresh
            binding.swiperefresh.isRefreshing = refreshState is LoadState.Loading
            binding.swiperefresh.isEnabled = false
            handleErrorEpisodesList(loadState)
        }

        //Include list divider
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rvEpisodes.addItemDecoration(dividerItemDecoration)

        //Bind Swipe to refresh
        binding.swiperefresh.setOnRefreshListener { episodesAdapter.retry() }
    }

    private fun handleErrorEpisodesList(loadState: CombinedLoadStates) {
        if (loadState.refresh is LoadState.Error || loadState.append is LoadState.Error || loadState.prepend is LoadState.Error) {
            binding.swiperefresh.isRefreshing = false
            binding.swiperefresh.isEnabled = true
            context?.let { Toast.makeText(it, getString(R.string.generic_error_episodes), Toast.LENGTH_SHORT).show() }
        }
    }

    private fun observeViewModelData() {
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

}