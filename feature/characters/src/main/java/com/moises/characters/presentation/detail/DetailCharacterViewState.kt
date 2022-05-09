package com.moises.characters.presentation.detail

import com.moises.characters.domain.model.Character

sealed class DetailCharacterViewState {
    class Success(val character: Character) : DetailCharacterViewState()
    class Failure(val errorMessage: String) : DetailCharacterViewState()
    object Loading : DetailCharacterViewState()
    object Empty : DetailCharacterViewState()
}