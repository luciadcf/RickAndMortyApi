package es.luciadcf.rickandmorty.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.luciadcf.rickandmorty.data.datasource.CharacterLocalDatasource
import es.luciadcf.rickandmorty.data.datasource.CharacterRemoteDatasource
import es.luciadcf.rickandmorty.data.mapper.dboToBo
import es.luciadcf.rickandmorty.data.mapper.dtoToBo
import es.luciadcf.rickandmorty.data.mapper.dtoToDbo
import es.luciadcf.rickandmorty.data.mapper.toBo
import es.luciadcf.rickandmorty.data.mapper.toDbo
import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.util.CustomError
import es.luciadcf.rickandmorty.data.util.Resource

interface CharacterRepository {

    suspend fun getCharacterList(
        page: Int, isRemote: Boolean, isNetworkAvailable: Boolean = false
    ): LiveData<Resource<List<CharacterBo>>>

    suspend fun getCharacterDetail(
        id: Int
    ): LiveData<Resource<CharacterBo>>

    suspend fun removeCharacter(character: CharacterBo): LiveData<Resource<Boolean>>

    suspend fun getLocalCharacterList(): LiveData<Resource<List<CharacterBo>>>

    suspend fun updateCharacter(character: CharacterBo): LiveData<Resource<Boolean>>
}

class CharacterRepositoryImpl(
    private val remoteDatasource: CharacterRemoteDatasource,
    private val localDatasource: CharacterLocalDatasource
) : CharacterRepository {

    private val charactersResponseLiveData = MutableLiveData<Resource<List<CharacterBo>>>()
    private val characterDetailLiveData = MutableLiveData<Resource<CharacterBo>>()
    private val removeCharacterLiveData = MutableLiveData<Resource<Boolean>>()
    private val updateCharacterLiveData = MutableLiveData<Resource<Boolean>>()

    override suspend fun getCharacterList(
        page: Int, isRemote: Boolean, isNetworkAvailable: Boolean
    ): LiveData<Resource<List<CharacterBo>>> {
        val localCharacters = localDatasource.getAllCharacters().dboToBo()
        if (!isRemote) {
            charactersResponseLiveData.postValue(
                Resource.success(
                    localCharacters
                )
            )
        } else {
            val response = remoteDatasource.getCharacters(page)
            if (response.isSuccessful) {
                val remoteCharacters = response.body()?.results ?: emptyList()
                localDatasource.insert(remoteCharacters.dtoToDbo())
                charactersResponseLiveData.postValue(
                    Resource.success(
                        remoteCharacters.dtoToBo()
                    )
                )
            } else {
                charactersResponseLiveData.postValue(
                    Resource.error(CustomError())
                )
            }
        }

        return charactersResponseLiveData
    }

    override suspend fun getCharacterDetail(
        id: Int
    ): LiveData<Resource<CharacterBo>> {
        val localCharacter = localDatasource.getCharacterById(id)
        characterDetailLiveData.postValue(Resource.success(localCharacter.toBo()))

        return characterDetailLiveData
    }

    override suspend fun removeCharacter(character: CharacterBo): LiveData<Resource<Boolean>> {
        localDatasource.removeCharacter(character.toDbo())
        removeCharacterLiveData.postValue(Resource.success(true))
        return removeCharacterLiveData
    }

    override suspend fun getLocalCharacterList(): LiveData<Resource<List<CharacterBo>>> {
        charactersResponseLiveData.postValue(
            Resource.success(
                localDatasource.getAllCharacters().dboToBo()
            )
        )
        return charactersResponseLiveData
    }

    override suspend fun updateCharacter(character: CharacterBo): LiveData<Resource<Boolean>> {
        localDatasource.insert(character.toDbo())
        updateCharacterLiveData.postValue(Resource.success(true))
        return updateCharacterLiveData
    }

}