package com.moises.characters.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.moises.characters.data.local.CharacterDao
import com.moises.characters.data.local.CharacterDatabase
import com.moises.characters.data.mapper.toCharacterEntity
import com.moises.characters.data.remote.service.CharacterApi
import com.moises.characters.domain.model.Character
import com.moises.characters.provider.TestDispatcherProvider
import com.moises.characters.util.mockRandomCharacterDTO
import com.moises.common.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import retrofit2.Response
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@Config(sdk = [29])
class CharacterRepositoryImplTest {


    private lateinit var subject: CharacterRepositoryImpl

    private lateinit var db: CharacterDatabase
    private lateinit var characterDao: CharacterDao
    private val api = mockk<CharacterApi>(relaxed = true)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CharacterDatabase::class.java).build()
        characterDao = db.characterDao
        subject = CharacterRepositoryImpl(api, db, TestDispatcherProvider())
    }

    @Test
    fun `getCharacter() forcing remote data - should return network response success data and insert remote data on database`() =
        runBlocking {
            val mockedId = 3
            val mockedCharacterRemote = mockRandomCharacterDTO(mockedId)
            coEvery { api.getCharacter(any()) } returns Response.success(mockedCharacterRemote)

            val testResult = subject.getCharacter(mockedId, true)
            val firstResult = testResult.first()
            val lastResult = testResult.last()

            //First resource type emitted should be loading state
            assertTrue(firstResult is Resource.Loading<Character>)

            //Last resource type emitted should be Success state with character as data
            assertTrue(lastResult is Resource.Success<Character>)

            Assert.assertEquals(lastResult.data?.name, mockedCharacterRemote.name)
            Assert.assertEquals(lastResult.data?.id, mockedCharacterRemote.id)
            Assert.assertEquals(lastResult.data?.created, mockedCharacterRemote.created)
            Assert.assertEquals(lastResult.data?.image, mockedCharacterRemote.image)
            Assert.assertEquals(lastResult.data?.gender, mockedCharacterRemote.gender)
            Assert.assertEquals(lastResult.data?.species, mockedCharacterRemote.species)
            Assert.assertEquals(lastResult.data?.status, mockedCharacterRemote.status)
            Assert.assertEquals(lastResult.data?.type, mockedCharacterRemote.type)

            // Verify data inserted on data is the same as remote data
            val cachedCharacter = characterDao.getCharacterById(mockedId)

            Assert.assertEquals(cachedCharacter.name, mockedCharacterRemote.name)
            Assert.assertEquals(cachedCharacter.id, mockedCharacterRemote.id)
            Assert.assertEquals(cachedCharacter.created, mockedCharacterRemote.created)
            Assert.assertEquals(cachedCharacter.image, mockedCharacterRemote.image)
            Assert.assertEquals(cachedCharacter.gender, mockedCharacterRemote.gender)
            Assert.assertEquals(cachedCharacter.species, mockedCharacterRemote.species)
            Assert.assertEquals(cachedCharacter.status, mockedCharacterRemote.status)
            Assert.assertEquals(cachedCharacter.type, mockedCharacterRemote.type)
        }

    @Test
    fun `getCharacter() not forcing remote data, with cached data - should not call network and return cached data from database`() =
        runBlocking {
            val mockedId = 3
            val mockedCharacterRemote = mockRandomCharacterDTO(mockedId)
            coEvery { api.getCharacter(any()) } returns Response.success(mockedCharacterRemote)

            //Include random character at data base
            val mockedCharacterCached = mockRandomCharacterDTO(mockedId)
            characterDao.insertCharacter(mockedCharacterCached.toCharacterEntity())

            val testResult = subject.getCharacter(mockedId, false)
            val firstResult = testResult.first()
            val lastResult = testResult.last()

            //First resource type emitted should be loading state
            assertTrue(firstResult is Resource.Loading<Character>)

            //Last resource type emitted should be Success state with character as data
            assertTrue(lastResult is Resource.Success<Character>)

            //Verify if the remote data was fetched
            coVerify(inverse = true) { api.getCharacter(mockedId) }

            //Verify if the response data is the same inserted on database
            Assert.assertEquals(lastResult.data?.name, mockedCharacterCached.name)
            Assert.assertEquals(lastResult.data?.id, mockedCharacterCached.id)
            Assert.assertEquals(lastResult.data?.created, mockedCharacterCached.created)
            Assert.assertEquals(lastResult.data?.image, mockedCharacterCached.image)
            Assert.assertEquals(lastResult.data?.gender, mockedCharacterCached.gender)
            Assert.assertEquals(lastResult.data?.species, mockedCharacterCached.species)
            Assert.assertEquals(lastResult.data?.status, mockedCharacterCached.status)
            Assert.assertEquals(lastResult.data?.type, mockedCharacterCached.type)
        }

    @Test
    fun `getCharacter() not forcing remote data, with empty cached data - should return network response success data and insert remote data on database`() =
        runBlocking {
            val mockedId = 3
            val mockedCharacterRemote = mockRandomCharacterDTO(mockedId)
            coEvery { api.getCharacter(any()) } returns Response.success(mockedCharacterRemote)

            val testResult = subject.getCharacter(mockedId, false)
            val firstResult = testResult.first()
            val lastResult = testResult.last()

            //First resource type emitted should be loading state
            assertTrue(firstResult is Resource.Loading<Character>)

            //Last resource type emitted should be Success state with character as data
            assertTrue(lastResult is Resource.Success<Character>)

            //Verify if the remote data was fetched
            coVerify { api.getCharacter(mockedId) }

            //First resource type emitted should be loading state
            assertTrue(firstResult is Resource.Loading<Character>)

            //Last resource type emitted should be Success state with character as data
            assertTrue(lastResult is Resource.Success<Character>)

            Assert.assertEquals(lastResult.data?.name, mockedCharacterRemote.name)
            Assert.assertEquals(lastResult.data?.id, mockedCharacterRemote.id)
            Assert.assertEquals(lastResult.data?.created, mockedCharacterRemote.created)
            Assert.assertEquals(lastResult.data?.image, mockedCharacterRemote.image)
            Assert.assertEquals(lastResult.data?.gender, mockedCharacterRemote.gender)
            Assert.assertEquals(lastResult.data?.species, mockedCharacterRemote.species)
            Assert.assertEquals(lastResult.data?.status, mockedCharacterRemote.status)
            Assert.assertEquals(lastResult.data?.type, mockedCharacterRemote.type)

            // Verify data inserted on data is the same as remote data
            val cachedCharacter = characterDao.getCharacterById(mockedId)

            Assert.assertEquals(cachedCharacter.name, mockedCharacterRemote.name)
            Assert.assertEquals(cachedCharacter.id, mockedCharacterRemote.id)
            Assert.assertEquals(cachedCharacter.created, mockedCharacterRemote.created)
            Assert.assertEquals(cachedCharacter.image, mockedCharacterRemote.image)
            Assert.assertEquals(cachedCharacter.gender, mockedCharacterRemote.gender)
            Assert.assertEquals(cachedCharacter.species, mockedCharacterRemote.species)
            Assert.assertEquals(cachedCharacter.status, mockedCharacterRemote.status)
            Assert.assertEquals(cachedCharacter.type, mockedCharacterRemote.type)
        }

    @Test
    fun `getCharacter() should return error`() =
        runBlocking {
            ///Mock for remote characters data with response error
            coEvery { api.getCharacters(any()) } returns Response.error(
                400,
                "{\"error\":[\"someerror\"]}"
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )

            val testResult = subject.getCharacter(1, true)
            val firstResult = testResult.first()
            val lastResult = testResult.last()

            //First resource type emitted should be loading state
            assertTrue(firstResult is Resource.Loading<Character>)

            //Last resource type emitted should be Error state
            assertTrue(lastResult is Resource.Error<Character>)
        }


    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

}