package es.luciadcf.rickandmorty.extension

fun String.convertToQuery() = this.replace(" ", "+").trim()

