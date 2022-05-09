package com.moises.characters.domain.repository

import androidx.paging.PagingData
import com.moises.characters.domain.model.Character
import com.moises.common.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getPagedCharacters(): Flow<PagingData<Character>>

    suspend fun getCharacter(characterId: Int, forceRemote: Boolean): Flow<Resource<Character>>

}