package com.moises.rickymorty.presentation.home

import androidx.lifecycle.ViewModel
import com.moises.rickymorty.domain.repository.CharacterRepository
import com.moises.rickymorty.domain.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val characterRepository: CharacterRepository, private val episodeRepository: EpisodeRepository) :
    ViewModel() {

    fun getCharacters() = characterRepository.getPagedCharacters()

    fun getEpisodes() = episodeRepository.getPagedEpisodes()
}