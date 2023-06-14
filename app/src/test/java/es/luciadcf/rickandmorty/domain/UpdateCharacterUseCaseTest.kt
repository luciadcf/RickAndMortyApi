package es.luciadcf.rickandmorty.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.repository.CharacterRepository
import es.luciadcf.rickandmorty.data.util.Resource
import es.luciadcf.rickandmorty.ui.domain.GetCharacterDetailUseCase
import es.luciadcf.rickandmorty.ui.domain.UpdateCharacterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UpdateCharacterUseCaseTest {

    @Mock
    lateinit var repository: CharacterRepository

    private lateinit var myUseCase: UpdateCharacterUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        myUseCase = UpdateCharacterUseCase(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testUpdateCharactersUseCase() = runTest {
        val testData = MutableLiveData(Resource.success(true))
        val expectedProcessedData = true

        Mockito.`when`(repository.updateCharacter(CharacterBo(0))).thenReturn(testData)

        val result = myUseCase.invoke(CharacterBo(0))

        Mockito.verify(repository).updateCharacter(CharacterBo(0))
        assertEquals(expectedProcessedData, result.value?.data)
    }
}