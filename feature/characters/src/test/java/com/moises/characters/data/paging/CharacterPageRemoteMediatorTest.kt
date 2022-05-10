package com.moises.characters.data.paging

import android.content.Context
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.moises.characters.data.local.CharacterDao
import com.moises.characters.data.local.CharacterDatabase
import com.moises.characters.data.local.CharacterEntity
import com.moises.characters.data.remote.service.CharacterApi
import com.moises.characters.util.mockCharactersDto
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import retrofit2.Response
import java.io.IOException

@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [29])
class CharacterPageRemoteMediatorTest {

    private lateinit var db: CharacterDatabase
    private lateinit var characterDao: CharacterDao
    private val api = mockk<CharacterApi>(relaxed = true)

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CharacterDatabase::class.java).build()
        characterDao = db.characterDao
    }

    @Test
    fun `testing load() - Refresh Load returns Success Result when more data is present`() = runBlocking {
        //Mocks for remote characters data with success response containing list of 10 character
        coEvery { api.getCharacters(any()) } returns Response.success(mockCharactersDto(10))
        val subject = CharacterPageRemoteMediator(db, api)
        val pagingState = PagingState<Int, CharacterEntity>(listOf(), null, PagingConfig(10), 10)
        val result = subject.load(LoadType.REFRESH, pagingState)
        //Verify is the Mediator result is Success
        assertTrue(result is RemoteMediator.MediatorResult.Success)
    }

    @Test
    fun `testing load() - Refresh Load returns Success Result but with empty list`() = runBlocking {
        //Mocks for remote characters data with success response containing empty list
        coEvery { api.getCharacters(any()) } returns Response.success(mockCharactersDto(0))
        val subject = CharacterPageRemoteMediator(db, api)
        val pagingState = PagingState<Int, CharacterEntity>(listOf(), null, PagingConfig(10), 10)
        val result = subject.load(LoadType.REFRESH, pagingState)

        //Verify is the Mediator result is Success
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        //Verify if the end of the page is reached
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `testing load() - Refresh Load returns Error Result when can not get data`() = runBlocking {
        ///Mock for remote characters data with response error
        coEvery { api.getCharacters(any()) } returns Response.error(
            400,
            "{\"error\":[\"someerror\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )

        val subject = CharacterPageRemoteMediator(db, api)
        val pagingState = PagingState<Int, CharacterEntity>(listOf(), null, PagingConfig(10), 10)
        val result = subject.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }
}
