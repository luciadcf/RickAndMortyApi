package es.luciadcf.rickandmorty.data.util

object StringUtil {

    fun convertFromQuery(text: String) = text.replace("+", " ").trim()

}