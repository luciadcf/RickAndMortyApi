package es.luciadcf.rickandmorty.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.util.Resource
import es.luciadcf.rickandmorty.ui.domain.GetCharactersUseCase
import es.luciadcf.rickandmorty.ui.domain.RemoveCharacterUseCase
import es.luciadcf.rickandmorty.ui.viewmodel.CharactersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CharactersViewModelTest {

    // Necesitas la regla InstantTaskExecutorRule para pruebas LiveData
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Anotar los objetos a simular con @Mock
    @Mock
    lateinit var listObserver: Observer<Resource<List<CharacterBo>>>

    @Mock
    lateinit var removeObserver: Observer<Resource<Boolean>>

    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase

    @Mock
    lateinit var removeCharacterUseCase: RemoveCharacterUseCase

    private lateinit var charactersViewModel: CharactersViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)

        charactersViewModel = CharactersViewModel(
            getCharactersUseCase, removeCharacterUseCase
        ).apply {
            getCharactersData().observeForever(listObserver)
            getRemoveCharacterData().observeForever(removeObserver)
        }
    }

    @Test
    fun testRemoteList() {
        charactersViewModel.getCharacters(0, true)
        val expectedData = charactersViewModel.getCharactersData().value

        if (expectedData != null) {
            Mockito.verify(listObserver).onChanged(expectedData)
        }
    }

    @Test
    fun testRemoveCharacter() {
        charactersViewModel.removeCharacter(CharacterBo(0))

        val expectedData = charactersViewModel.getRemoveCharacterData().value

        if (expectedData != null) {
            Mockito.verify(removeObserver).onChanged(expectedData)
        }
    }
}



