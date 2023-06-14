package es.luciadcf.rickandmorty.ui.domain

import es.luciadcf.rickandmorty.data.repository.CharacterRepository

class GetCharacterDetailUseCase(
    private val charactersRepository: CharacterRepository
) {

    suspend operator fun invoke(id: Int) =
        charactersRepository.getCharacterDetail(id)

}