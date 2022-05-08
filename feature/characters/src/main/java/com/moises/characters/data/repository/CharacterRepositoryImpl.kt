package com.moises.characters.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.moises.characters.data.local.CharacterDatabase
import com.moises.characters.data.mapper.toCharacter
import com.moises.characters.data.remote.service.CharacterApi
import com.moises.characters.domain.CharacterRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val api: CharacterApi,
    private val db: CharacterDatabase
) : CharacterRepository {


    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedCharacters() = Pager(
        config = PagingConfig(20),
        remoteMediator = com.moises.characters.data.paging.CharacterPageRemoteMediator(db = db, characterApi = api)
    ) {
        db.characterDao.listCharacters()
    }.flow.map { it.map { item -> item.toCharacter() } }
}