package com.moises.rickymorty.domain.repository

import androidx.paging.PagingData
import com.moises.rickymorty.domain.model.Character
import com.moises.rickymorty.util.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getPagedCharacters(): Flow<PagingData<Character>>

}