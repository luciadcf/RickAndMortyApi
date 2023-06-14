package es.luciadcf.rickandmorty.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.repository.CharacterRepository
import es.luciadcf.rickandmorty.data.util.Resource
import es.luciadcf.rickandmorty.ui.domain.GetCharacterDetailUseCase
import es.luciadcf.rickandmorty.ui.domain.RemoveCharacterUseCase
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

class RemoveCharacterUseCaseTest {

    @Mock
    lateinit var repository: CharacterRepository

    private lateinit var myUseCase: RemoveCharacterUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        myUseCase = RemoveCharacterUseCase(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testRemoveCharacterUseCase() = runTest {
        val testData = MutableLiveData(Resource.success(true))
        val expectedProcessedData = true

        Mockito.`when`(repository.removeCharacter(CharacterBo(0))).thenReturn(testData)

        val result = myUseCase.invoke(CharacterBo(0))

        Mockito.verify(repository).removeCharacter(CharacterBo(0))
        assertEquals(expectedProcessedData, result.value?.data)
    }
}