package es.luciadcf.rickandmorty.data.model.dto

data class CharacterDto(
    val id: Int,
    val name: String? = null,
    val image: String? = null,
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null,
    val origin: ItemDto? = null,
    val location: ItemDto? = null
)