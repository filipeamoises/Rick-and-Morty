package com.moises.characters.util

import com.moises.characters.data.local.CharacterEntity
import com.moises.characters.data.mapper.toCharacter
import com.moises.characters.data.remote.dto.CharacterDto
import com.moises.characters.data.remote.dto.CharactersDto
import com.moises.characters.data.remote.dto.InfoCharacterDto
import com.moises.characters.domain.model.Character

fun mockListCharacters(size: Int = 5): List<Character> {
    val arrayCharacter = arrayListOf<Character>()
    while (size > arrayCharacter.size) {
        arrayCharacter.add(mockRandomCharacter(arrayCharacter.size))
    }
    return arrayCharacter.toList()
}

fun mockRandomCharacter(id: Int = 1): Character {
    return mockRandomCharacterDTO(id).toCharacter()
}

fun mockListCharacterDto(size: Int = 5): List<CharacterDto> {
    val arrayCharacter = arrayListOf<CharacterDto>()
    while (size > arrayCharacter.size) {
        arrayCharacter.add(mockRandomCharacterDTO(arrayCharacter.size))
    }
    return arrayCharacter.toList()
}

fun mockCharactersDto(size: Int = 5): CharactersDto {
    return CharactersDto(InfoCharacterDto(count = size), mockListCharacterDto(size))
}

fun mockRandomCharacterDTO(id: Int = 1): CharacterDto {
    return CharacterDto(
        id = id,
        created = "2021-10-15T17:00:24.102Z",
        episode = listOf("a", "b", "c"),
        name = "Rick Morty".random().toString(),
        url = "https://www.someurl.com/${id}",
        species = "Alien".random().toString(),
        type = "",
        gender = "Female",
        status = "Alive",
        image = "https://www.someurl.com/image/${id}",
    )
}

fun mockRandomCharacterEntity(id: Int = 1): CharacterEntity {
    return CharacterEntity(
        id = id,
        created = "2021-10-15T17:00:24.102Z",
        name = "Rick Morty".random().toString(),
        species = "Alien".random().toString(),
        type = "",
        gender = "Female",
        status = "Alive",
        image = "https://www.someurl.com/image/${id}"
    )
}