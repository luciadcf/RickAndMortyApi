package es.luciadcf.rickandmorty.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.repository.CharacterRepository
import es.luciadcf.rickandmorty.data.util.Resource
import es.luciadcf.rickandmorty.ui.domain.GetCharacterDetailUseCase
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

class GetCharacterDetailUseCaseTest {

    @Mock
    lateinit var repository: CharacterRepository

    private lateinit var myUseCase: GetCharacterDetailUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        myUseCase = GetCharacterDetailUseCase(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetDetailUseCase() = runTest {
        val testData =
            MutableLiveData(Resource.success(CharacterBo(0))) as LiveData<Resource<CharacterBo>>
        val expectedProcessedData = CharacterBo(0)

        Mockito.`when`(repository.getCharacterDetail(0)).thenReturn(testData)

        val result = myUseCase.invoke(0)

        Mockito.verify(repository).getCharacterDetail(0)
        assertEquals(expectedProcessedData.id, result.value?.data?.id)
    }
}