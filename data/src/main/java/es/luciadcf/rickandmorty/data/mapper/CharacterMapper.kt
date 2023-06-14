package es.luciadcf.rickandmorty.data.mapper

import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.model.dbo.CharacterDbo
import es.luciadcf.rickandmorty.data.model.dto.CharacterDto

fun CharacterDto.toBo() = CharacterBo(
    id, name, image, status, species, type, gender, origin?.name, location?.name
)

fun CharacterDto.toDbo() = CharacterDbo(
    id, name, image, status, species, type, gender, origin?.name, location?.name
)

fun CharacterDbo.toBo() = CharacterBo(
    id, name, image, status, species, type, gender, origin, location
)

fun CharacterBo.toDbo() = CharacterDbo(
    id, name, image, status, species, type, gender, origin, location
)

fun List<CharacterDto>.dtoToDbo() = map { it.toDbo() }

fun List<CharacterDto>.dtoToBo() = map { it.toBo() }

fun List<CharacterDbo>.dboToBo() = map { it.toBo() }