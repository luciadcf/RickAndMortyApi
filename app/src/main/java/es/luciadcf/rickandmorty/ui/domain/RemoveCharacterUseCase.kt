package es.luciadcf.rickandmorty.ui.domain

import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.repository.CharacterRepository

class RemoveCharacterUseCase(
    private val charactersRepository: CharacterRepository
) {

    suspend operator fun invoke(character: CharacterBo) = charactersRepository.removeCharacter(character)

}