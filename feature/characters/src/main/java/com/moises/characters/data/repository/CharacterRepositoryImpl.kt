package com.moises.characters.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.moises.characters.data.local.CharacterDatabase
import com.moises.characters.data.local.CharacterEntity
import com.moises.characters.data.mapper.toCharacter
import com.moises.characters.data.mapper.toCharacterEntity
import com.moises.characters.data.paging.CharacterPageRemoteMediator
import com.moises.characters.data.remote.service.CharacterApi
import com.moises.characters.domain.model.Character
import com.moises.characters.domain.repository.CharacterRepository
import com.moises.common.Resource
import com.moises.common.coroutine_utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val api: CharacterApi,
    private val db: CharacterDatabase,
    private val dispatcherProvider: DispatcherProvider
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedCharacters() = Pager(
        config = PagingConfig(20),
        remoteMediator = CharacterPageRemoteMediator(db = db, characterApi = api)
    ) {
        db.characterDao.listCharacters()
    }.flow.map { it.map { item -> item.toCharacter() } }

    override suspend fun getCharacter(characterId: Int, forceRemote: Boolean): Flow<Resource<Character>> {
        return flow {
            emit(Resource.Loading())
            //Get data from local database if its empty get from remote service
            try {
                var cachedCharacter: CharacterEntity? = null
                if (!forceRemote) {
                    withContext(dispatcherProvider.io) {
                        cachedCharacter = db.characterDao.getCharacterById(characterId)
                    }
                }

                if (forceRemote || cachedCharacter == null) {
                    val responseCharacter = try {
                        withContext(dispatcherProvider.io) {
                            api.getCharacter(characterId)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        emit(Resource.Error(message = "Oops... Some error happened. We could not load the data."))
                        null
                    } catch (e: HttpException) {
                        e.printStackTrace()
                        emit(Resource.Error(message = "Oops... Some error happened. We could not load the data. Please try again later."))
                        null
                    }

                    if (responseCharacter == null || !responseCharacter.isSuccessful) {
                        emit(Resource.Error(message = "Oops... Some error happened. We could not load the data. Please try again later."))
                        return@flow
                    }

                    responseCharacter.body().let { characterDto ->
                        //Update the local cache
                        withContext(dispatcherProvider.io) {
                            characterDto?.toCharacterEntity()?.let { db.characterDao.insertCharacter(it) }
                        }
                        //Emit the update data from remote
                        emit(Resource.Success(data = characterDto?.toCharacter()))
                    }

                } else {
                    emit(Resource.Success(data = cachedCharacter!!.toCharacter()))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Oops... Some error happened. We could not load the data."))
            }
        }
    }
}