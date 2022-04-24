package com.moises.rickymorty.data.mapper

import com.moises.rickymorty.data.local.EpisodeEntity
import com.moises.rickymorty.data.remote.dto.EpisodeDto
import com.moises.rickymorty.domain.model.Episode

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

