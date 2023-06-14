package es.luciadcf.rickandmorty.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.util.Resource
import es.luciadcf.rickandmorty.ui.domain.GetCharactersUseCase
import es.luciadcf.rickandmorty.ui.domain.RemoveCharacterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val removeCharacterUseCase: RemoveCharacterUseCase
) : ViewModel() {

    private val characters: MediatorLiveData<Resource<List<CharacterBo>>> = MediatorLiveData()
    private var charactersSource: LiveData<Resource<List<CharacterBo>>> = MutableLiveData()

    private val removeCharacter: MediatorLiveData<Resource<Boolean>> = MediatorLiveData()
    private var removeCharacterSource: LiveData<Resource<Boolean>> = MutableLiveData()

    fun getCharacters(page: Int = 1, isRemote: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            characters.value = Resource.loading()
            characters.removeSource(charactersSource)
            withContext(Dispatchers.IO) {
                charactersSource = getCharactersUseCase(page, isRemote)
            }
            characters.addSource(charactersSource) {
                characters.value = it
            }
        }
    }

    fun getCharactersData() = characters

    fun removeCharacter(character: CharacterBo) {
        viewModelScope.launch(Dispatchers.Main) {
            removeCharacter.value = Resource.loading()
            removeCharacter.removeSource(removeCharacterSource)
            withContext(Dispatchers.IO) {
                removeCharacterSource = removeCharacterUseCase(character)
            }
            removeCharacter.addSource(removeCharacterSource) {
                removeCharacter.value = it
            }
        }
    }

    fun getRemoveCharacterData() = removeCharacter

}