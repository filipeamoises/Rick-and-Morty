package com.moises.episodes.presentation.list

import androidx.lifecycle.ViewModel
import com.moises.episodes.domain.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(private val episodeRepository: EpisodeRepository) :
    ViewModel() {

    fun getEpisodes() = episodeRepository.getPagedEpisodes()
}