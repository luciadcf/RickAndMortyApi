package es.luciadcf.rickandmorty.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.repository.CharacterRepository
import es.luciadcf.rickandmorty.data.util.Resource
import es.luciadcf.rickandmorty.ui.domain.GetCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetCharactersUseCaseTest {

    @Mock
    lateinit var repository: CharacterRepository

    private lateinit var myUseCase: GetCharactersUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        myUseCase = GetCharactersUseCase(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetCharactersUseCase() = runTest {
        val testData =
            MutableLiveData(Resource.success(arrayListOf(CharacterBo(1)))) as LiveData<Resource<List<CharacterBo>>>
        val expectedProcessedData = Resource.success(arrayListOf(CharacterBo(1)))

        Mockito.`when`(repository.getCharacterList(0, true)).thenReturn(testData)

        val result = myUseCase.invoke(0, true)

        Mockito.verify(repository).getCharacterList(0, true)
        assertEquals(expectedProcessedData.data?.get(0)?.id ?: -1, result.value?.data?.get(0)?.id)
    }
}