package com.moises.characters.presentation.viewModel

import com.moises.characters.domain.repository.CharacterRepository
import com.moises.characters.presentation.detail.DetailCharacterViewModel
import com.moises.characters.presentation.detail.DetailCharacterViewState
import com.moises.characters.util.mockRandomCharacter
import com.moises.common.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class DetailCharacterViewModelTest {

    private lateinit var subject: DetailCharacterViewModel

    @MockK
    private lateinit var repository: CharacterRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        subject = DetailCharacterViewModel(repository)
    }

    @Ignore
    @Test
    fun `getCharacter(characterId) - should return Resource response success data`() =
        runBlocking {
            val mockedCharacter = mockRandomCharacter(1)
            coEvery { repository.getCharacter(any(), any()) } returns flow {
                emit(Resource.Success(data = mockedCharacter))
            }

            subject.getCharacter(1)

            val successResult = (subject.characterState.last() as DetailCharacterViewState.Success).character

            //Verify if the final Status is Success status
            assertTrue(subject.characterState.first() is DetailCharacterViewState.Loading)
            assertTrue(subject.characterState.last() is DetailCharacterViewState.Success)

            Assert.assertEquals(mockedCharacter.name, successResult.name)
            Assert.assertEquals(mockedCharacter.id, successResult.id)
            Assert.assertEquals(mockedCharacter.created, successResult.created)
            Assert.assertEquals(mockedCharacter.image, successResult.image)
            Assert.assertEquals(mockedCharacter.gender, successResult.gender)
            Assert.assertEquals(mockedCharacter.species, successResult.species)
            Assert.assertEquals(mockedCharacter.status, successResult.status)
            Assert.assertEquals(mockedCharacter.type, successResult.type)
        }

}