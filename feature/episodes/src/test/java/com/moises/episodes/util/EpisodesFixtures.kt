package com.moises.episodes.util

import com.moises.episodes.data.local.EpisodeEntity
import com.moises.episodes.data.mapper.toEpisode
import com.moises.episodes.data.remote.dto.EpisodeDto
import com.moises.episodes.data.remote.dto.EpisodesDto
import com.moises.episodes.data.remote.dto.InfoDto
import com.moises.episodes.domain.model.Episode


fun mockListEpisodes(size: Int = 5): List<Episode> {
    val arrayEpisodes = arrayListOf<Episode>()
    while (size > arrayEpisodes.size) {
        arrayEpisodes.add(mockRandomEpisode(arrayEpisodes.size))
    }
    return arrayEpisodes.toList()
}

fun mockRandomEpisode(id: Int = 1): Episode {
    return mockRandomEpisodeDTO(id).toEpisode()
}

fun mockListEpisodeDto(size: Int = 5): List<EpisodeDto> {
    val arrayEpisodes = arrayListOf<EpisodeDto>()
    while (size > arrayEpisodes.size) {
        arrayEpisodes.add(mockRandomEpisodeDTO(arrayEpisodes.size))
    }
    return arrayEpisodes.toList()
}

fun mockEpisodesDto(size: Int = 5): EpisodesDto {
    return EpisodesDto(info = InfoDto(count = size), mockListEpisodeDto(size))
}

fun mockRandomEpisodeDTO(id: Int = 1): EpisodeDto {
    return EpisodeDto(
        id = id,
        airDate = "July 4, 2021",
        created = "2021-10-15T17:00:24.102Z",
        episode = "S${id}E${id * 2}",
        name = "Star Mort: Rickturn of the Jerri".random().toString(),
        url = "https://www.someurl.com/${id}"
    )
}

fun mockRandomEpisodeEntity(id: Int = 1): EpisodeEntity {
    return EpisodeEntity(
        id = id,
        airDate = "July 4, 2021",
        created = "2021-10-15T17:00:24.102Z",
        episode = "S${id}E${id * 2}",
        name = "Star Mort: Rickturn of the Jerri".random().toString(),
        url = "https://www.someurl.com/${id}"
    )
}