package es.luciadcf.rickandmorty.data.model.bo

data class CharacterBo(
    val id: Int,
    val name: String? = null,
    val image: String? = null,
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null,
    val origin: String? = null,
    val location: String? = null
)
