package es.luciadcf.rickandmorty.ui.domain

import es.luciadcf.rickandmorty.data.repository.CharacterRepository

class GetCharactersUseCase(
    private val charactersRepository: CharacterRepository
) {

    suspend operator fun invoke(page: Int, isRemote: Boolean) = charactersRepository.getCharacterList(page, isRemote)

}