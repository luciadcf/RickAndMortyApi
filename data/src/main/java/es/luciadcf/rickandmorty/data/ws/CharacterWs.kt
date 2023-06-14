package es.luciadcf.rickandmorty.data.ws

import es.luciadcf.rickandmorty.data.model.dto.CharacterDto
import es.luciadcf.rickandmorty.data.model.dto.CharacterResponseDto
import retrofit2.Response
import retrofit2.http.*

interface CharacterWs {

    @Headers("Content-Type: application/json")
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<CharacterResponseDto>

    @Headers("Content-Type: application/json")
    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): Response<CharacterDto>

}