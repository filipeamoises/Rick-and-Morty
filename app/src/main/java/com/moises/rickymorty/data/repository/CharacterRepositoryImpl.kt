package com.moises.rickymorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.moises.rickymorty.R
import com.moises.rickymorty.data.local.CharacterDao
import com.moises.rickymorty.data.local.RickyMortyDatabase
import com.moises.rickymorty.data.mapper.toCharacter
import com.moises.rickymorty.data.mapper.toCharacterEntity
import com.moises.rickymorty.data.paging.CharacterPageRemoteMediator
import com.moises.rickymorty.data.remote.service.CharacterApi
import com.moises.rickymorty.domain.model.Character
import com.moises.rickymorty.domain.repository.CharacterRepository
import com.moises.rickymorty.util.DispatcherProvider
import com.moises.rickymorty.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val api: CharacterApi,
    private val db: RickyMortyDatabase
) : CharacterRepository {


    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedCharacters() = Pager(
        config = PagingConfig(20),
        remoteMediator = CharacterPageRemoteMediator(db = db, characterApi = api)
    ) {
        db.characterDao.listCharacters()
    }.flow.map { it.map { item -> item.toCharacter() } }
}