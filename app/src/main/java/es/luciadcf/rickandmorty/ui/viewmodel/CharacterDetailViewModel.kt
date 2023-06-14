package es.luciadcf.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.util.Resource
import es.luciadcf.rickandmorty.ui.domain.GetCharacterDetailUseCase
import es.luciadcf.rickandmorty.ui.domain.UpdateCharacterUseCase
import es.luciadcf.rickandmorty.util.CustomMediatorLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val updateCharacterUseCase: UpdateCharacterUseCase
) : ViewModel() {

    private val characterDetail: CustomMediatorLiveData<Resource<CharacterBo>> =
        CustomMediatorLiveData()

    private val updateCharacter: CustomMediatorLiveData<Resource<Boolean>> =
        CustomMediatorLiveData()

    fun getCharacterDetail(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            characterDetail.setData(Resource.loading())
            withContext(Dispatchers.IO) {
                characterDetail.changeSource(
                    this, Dispatchers.Main, getCharacterDetailUseCase(id)
                )
            }
        }
    }

    fun getCharacterDetailData() = characterDetail.liveData()

    fun updateCharacter(characterBo: CharacterBo) {
        viewModelScope.launch(Dispatchers.Main) {
            updateCharacter.setData(Resource.loading())
            withContext(Dispatchers.IO) {
                updateCharacter.changeSource(
                    this, Dispatchers.Main, updateCharacterUseCase(characterBo)
                )
            }
        }
    }

    fun getUpdateCharacterData() = updateCharacter.liveData()


}