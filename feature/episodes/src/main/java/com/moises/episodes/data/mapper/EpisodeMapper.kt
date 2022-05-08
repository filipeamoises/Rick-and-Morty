package com.moises.episodes.data.mapper

import com.moises.episodes.data.local.EpisodeEntity
import com.moises.episodes.data.remote.dto.EpisodeDto
import com.moises.episodes.domain.model.Episode

fun EpisodeEntity.toEpisode(): Episode {
    return Episode(
        id = id,
        airDate = airDate,
        name = name,
        url = url,
        created = created,
        episode = episode
    )
}

fun EpisodeDto.toEpisodeEntity(): EpisodeEntity {
    return EpisodeEntity(
        id = id,
        airDate = airDate,
        name = name,
        url = url,
        created = created,
        episode = episode
    )
}

fun EpisodeDto.toEpisode(): Episode {
    return Episode(
        id = id,
        airDate = airDate,
        name = name,
        url = url,
        created = created,
        episode = episode
    )
}

