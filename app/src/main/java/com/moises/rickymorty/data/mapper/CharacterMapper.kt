package com.moises.rickymorty.data.mapper

import com.moises.rickymorty.data.local.CharacterEntity
import com.moises.rickymorty.data.remote.dto.CharacterDto
import com.moises.rickymorty.domain.model.Character

fun CharacterEntity.toCharacter(): Character {
    return Character(created = created,
    gender = gender,
    id = id,
    image = image,
    name = name,
    species = species,
    status = status,
    type = type)
}

fun CharacterDto.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(created = created,
        gender = gender,
        id = id,
        image = image,
        name = name,
        species = species,
        status = status,
        type = type)
}

fun CharacterDto.toCharacter(): Character {
    return Character(created = created,
        gender = gender,
        id = id,
        image = image,
        name = name,
        species = species,
        status = status,
        type = type)
}

