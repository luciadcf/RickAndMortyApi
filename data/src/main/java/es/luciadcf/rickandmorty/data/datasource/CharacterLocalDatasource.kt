package es.luciadcf.rickandmorty.data.datasource

import es.luciadcf.rickandmorty.data.database.dao.CharacterDao
import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.model.dbo.CharacterDbo

interface CharacterLocalDatasource {

    suspend fun getAllCharacters(): List<CharacterDbo>

    suspend fun insert(character: CharacterDbo)
    suspend fun insert(characters: List<CharacterDbo>)
    suspend fun getCharacterById(id: Int): CharacterDbo
    suspend fun removeCharacter(character: CharacterDbo)
}

class CharacterLocalDatasourceImpl(
    private val characterDao: CharacterDao
) : CharacterLocalDatasource {
    override suspend fun getAllCharacters() = characterDao.getAll()
    override suspend fun insert(character: CharacterDbo) {
        characterDao.insert(character)
    }

    override suspend fun insert(characters: List<CharacterDbo>) {
        characterDao.insert(characters)
    }

    override suspend fun getCharacterById(id: Int): CharacterDbo {
        return characterDao.getById(id)
    }

    override suspend fun removeCharacter(character: CharacterDbo) {
        characterDao.remove(character)
    }

}