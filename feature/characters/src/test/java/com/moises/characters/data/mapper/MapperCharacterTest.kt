package com.moises.characters.data.mapper

import com.moises.characters.util.mockRandomCharacterDTO
import com.moises.characters.util.mockRandomCharacterEntity

import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * Unit tests, which will test the episode mappers).
 */
class MapperCharacterTest {

    @Test
    fun `Test CharacterDto to CharacterEntity should has the same values`() {
        val characterDto = mockRandomCharacterDTO()
        val characterEntity = characterDto.toCharacterEntity()

        assertEquals(characterDto.name, characterEntity.name)
        assertEquals(characterDto.id, characterEntity.id)
        assertEquals(characterDto.created, characterEntity.created)
        assertEquals(characterDto.image, characterEntity.image)
        assertEquals(characterDto.gender, characterEntity.gender)
        assertEquals(characterDto.species, characterEntity.species)
        assertEquals(characterDto.status, characterEntity.status)
        assertEquals(characterDto.type, characterEntity.type)
    }

    @Test
    fun `Test CharacterDto to Character should has the same values`() {
        val characterDto = mockRandomCharacterDTO()
        val character = characterDto.toCharacter()

        assertEquals(characterDto.name, character.name)
        assertEquals(characterDto.id, character.id)
        assertEquals(characterDto.created, character.created)
        assertEquals(characterDto.image, character.image)
        assertEquals(characterDto.gender, character.gender)
        assertEquals(characterDto.species, character.species)
        assertEquals(characterDto.status, character.status)
        assertEquals(characterDto.type, character.type)
    }

    @Test
    fun `Test CharacterEntity to Character should has the same values`() {
        val characterEntity = mockRandomCharacterEntity()
        val character = characterEntity.toCharacter()

        assertEquals(characterEntity.name, character.name)
        assertEquals(characterEntity.id, character.id)
        assertEquals(characterEntity.created, character.created)
        assertEquals(characterEntity.image, character.image)
        assertEquals(characterEntity.gender, character.gender)
        assertEquals(characterEntity.species, character.species)
        assertEquals(characterEntity.status, character.status)
        assertEquals(characterEntity.type, character.type)
    }
}