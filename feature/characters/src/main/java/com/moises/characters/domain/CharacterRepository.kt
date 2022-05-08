package com.moises.characters.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getPagedCharacters(): Flow<PagingData<Character>>

}