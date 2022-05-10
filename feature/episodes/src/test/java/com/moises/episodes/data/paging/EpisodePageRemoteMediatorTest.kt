package com.moises.episodes.data.paging

import android.content.Context
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.moises.episodes.data.local.EpisodeDao
import com.moises.episodes.data.local.EpisodeDatabase
import com.moises.episodes.data.local.EpisodeEntity
import com.moises.episodes.data.remote.service.EpisodeApi
import com.moises.episodes.util.mockEpisodesDto
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
class EpisodePageRemoteMediatorTest {

    private lateinit var db: EpisodeDatabase
    private lateinit var episodeDao: EpisodeDao
    private val api = mockk<EpisodeApi>(relaxed = true)

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, EpisodeDatabase::class.java).build()
        episodeDao = db.episodeDao
    }

    @Test
    fun `testing load() - Refresh Load returns Success Result when more data is present`() = runBlocking {
        //Mocks for remote episodes data with success response containing list of 10 episode
        coEvery { api.getEpisodes(any()) } returns Response.success(mockEpisodesDto(10))
        val subject = EpisodePageRemoteMediator(db, api)
        val pagingState = PagingState<Int, EpisodeEntity>(listOf(), null, PagingConfig(10), 10)
        val result = subject.load(LoadType.REFRESH, pagingState)
        //Verify is the Mediator result is Success
        assertTrue(result is RemoteMediator.MediatorResult.Success)
    }

    @Test
    fun `testing load() - Refresh Load returns Success Result but with empty list`() = runBlocking {
        //Mocks for remote episodes data with success response containing empty list
        coEvery { api.getEpisodes(any()) } returns Response.success(mockEpisodesDto(0))
        val subject = EpisodePageRemoteMediator(db, api)
        val pagingState = PagingState<Int, EpisodeEntity>(listOf(), null, PagingConfig(10), 10)
        val result = subject.load(LoadType.REFRESH, pagingState)

        //Verify is the Mediator result is Success
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        //Verify if the end of the page is reached
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `testing load() - Refresh Load returns Error Result when can not get data`() = runBlocking {
        ///Mock for remote episodes data with response error
        coEvery { api.getEpisodes(any()) } returns Response.error(
            400,
            "{\"error\":[\"someerror\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )

        val subject = EpisodePageRemoteMediator(db, api)
        val pagingState = PagingState<Int, EpisodeEntity>(listOf(), null, PagingConfig(10), 10)
        val result = subject.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }
}
