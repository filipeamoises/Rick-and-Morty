package com.moises.characters.presentation.list

import androidx.lifecycle.ViewModel
import com.moises.characters.domain.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListCharactersViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {

    fun getCharacters() = characterRepository.getPagedCharacters()
}