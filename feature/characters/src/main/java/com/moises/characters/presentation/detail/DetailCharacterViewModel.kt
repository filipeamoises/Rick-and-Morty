package com.moises.characters.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moises.characters.domain.repository.CharacterRepository
import com.moises.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCharacterViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {

    private val _characterState = MutableStateFlow<DetailCharacterViewState>(DetailCharacterViewState.Empty)
    val characterState: StateFlow<DetailCharacterViewState> = _characterState

    fun getCharacter(characterId: Int) {
        viewModelScope.launch {
            characterRepository.getCharacter(characterId, false).collect { result ->
                _characterState.value = when (result) {
                    is Resource.Success -> {
                        if (result.data != null) {
                            DetailCharacterViewState.Success(character = result.data!!)
                        } else {
                            DetailCharacterViewState.Failure(errorMessage = "Something went wrong")
                        }
                    }
                    is Resource.Error -> {
                        DetailCharacterViewState.Failure(errorMessage = result.message ?: "Generic Error Message")
                    }
                    is Resource.Loading -> {
                        DetailCharacterViewState.Loading
                    }
                }
            }
        }
    }
}