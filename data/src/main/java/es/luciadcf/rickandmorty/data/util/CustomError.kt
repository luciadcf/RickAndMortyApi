package es.luciadcf.rickandmorty.data.util

enum class ErrorType {
    UNKNOWN_ERROR
}

data class CustomError(
    val serverErrorMessage: String? = null,
    val type: ErrorType = ErrorType.UNKNOWN_ERROR
)