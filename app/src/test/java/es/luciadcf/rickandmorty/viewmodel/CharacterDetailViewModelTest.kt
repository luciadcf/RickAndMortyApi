package es.luciadcf.rickandmorty.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.util.Resource
import es.luciadcf.rickandmorty.ui.domain.GetCharacterDetailUseCase
import es.luciadcf.rickandmorty.ui.domain.UpdateCharacterUseCase
import es.luciadcf.rickandmorty.ui.viewmodel.CharacterDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CharacterDetailViewModelTest {

    // Necesitas la regla InstantTaskExecutorRule para pruebas LiveData
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Anotar los objetos a simular con @Mock
    @Mock
    lateinit var detailObserver: Observer<Resource<CharacterBo>>

    @Mock
    lateinit var updateObserver: Observer<Resource<Boolean>>

    @Mock
    lateinit var getCharacterDetailUseCase: GetCharacterDetailUseCase

    @Mock
    lateinit var updateCharacterUseCase: UpdateCharacterUseCase

    private lateinit var characterDetailViewModel: CharacterDetailViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)

        characterDetailViewModel = CharacterDetailViewModel(getCharacterDetailUseCase, updateCharacterUseCase).apply {
            getCharacterDetailData().observeForever(detailObserver)
            getUpdateCharacterData().observeForever(updateObserver)
        }
    }

    @Test
    fun testGetDetail() {
        characterDetailViewModel.getCharacterDetail(1)
        val expectedData = characterDetailViewModel.getCharacterDetailData().value

        if (expectedData != null) {
            Mockito.verify(detailObserver).onChanged(expectedData)
        }
    }

    @Test
    fun testUpdateCharacter() {
        characterDetailViewModel.updateCharacter(CharacterBo(1, "Name changed", "", ""))

        val expectedData = characterDetailViewModel.getUpdateCharacterData().value

        if (expectedData != null) {
            Mockito.verify(updateObserver).onChanged(expectedData)
        }
    }
}



