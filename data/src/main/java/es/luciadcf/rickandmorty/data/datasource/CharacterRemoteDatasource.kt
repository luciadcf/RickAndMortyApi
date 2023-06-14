package es.luciadcf.rickandmorty.data.datasource

import es.luciadcf.rickandmorty.data.model.dto.CharacterDto
import es.luciadcf.rickandmorty.data.model.dto.CharacterResponseDto
import es.luciadcf.rickandmorty.data.ws.CharacterWs
import retrofit2.Response

interface CharacterRemoteDatasource {
    suspend fun getCharacters(page: Int): Response<CharacterResponseDto>
    suspend fun getCharacter(id: Int): Response<CharacterDto>
}

class CharacterRemoteDatasourceImpl(
    private val characterWs: CharacterWs
) : CharacterRemoteDatasource {

    override suspend fun getCharacters(page: Int) = characterWs.getCharacters(page)

    override suspend fun getCharacter(id: Int) = characterWs.getCharacter(id)

}