package com.moises.episodes.data.mapper

import com.moises.episodes.data.mapper.toEpisode
import com.moises.episodes.data.mapper.toEpisodeEntity
import com.moises.episodes.util.mockRandomEpisodeDTO
import com.moises.episodes.util.mockRandomEpisodeEntity
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests, which will test the episode mappers).
 */
class MapperEpisodesTest {

    @Test
    fun `Test EpisodeDto to EpisodeEntity should has the same values`() {
        val episodeDto = mockRandomEpisodeDTO()
        val episodeEntity = episodeDto.toEpisodeEntity()

        assertEquals(episodeDto.name, episodeEntity.name)
        assertEquals(episodeDto.episode, episodeEntity.episode)
        assertEquals(episodeDto.id, episodeEntity.id)
        assertEquals(episodeDto.airDate, episodeEntity.airDate)
        assertEquals(episodeDto.url, episodeEntity.url)
    }

    @Test
    fun `Test EpisodeDto to Episode should has the same values`() {
        val episodeDto = mockRandomEpisodeDTO()
        val episode = episodeDto.toEpisode()

        assertEquals(episodeDto.name, episode.name)
        assertEquals(episodeDto.episode, episode.episode)
        assertEquals(episodeDto.id, episode.id)
        assertEquals(episodeDto.airDate, episode.airDate)
        assertEquals(episodeDto.url, episode.url)
    }

    @Test
    fun `Test EpisodeEntity to Episode should has the same values`() {
        val episodeEntity = mockRandomEpisodeEntity()
        val episode = episodeEntity.toEpisode()

        assertEquals(episodeEntity.name, episode.name)
        assertEquals(episodeEntity.episode, episode.episode)
        assertEquals(episodeEntity.id, episode.id)
        assertEquals(episodeEntity.airDate, episode.airDate)
        assertEquals(episodeEntity.url, episode.url)
    }
}