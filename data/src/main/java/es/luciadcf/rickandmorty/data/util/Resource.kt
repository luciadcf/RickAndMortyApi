package es.luciadcf.rickandmorty.data.util

data class Resource<out T>(val status: Status, val data: T?, val error: CustomError?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(error: CustomError, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                error
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    fun isSuccess(): Boolean {
        return status == Status.SUCCESS
    }

    fun isError(): Boolean {
        return status == Status.ERROR
    }

    fun isLoading(): Boolean {
        return status == Status.LOADING
    }

    fun isNotError(): Boolean {
        return status == Status.SUCCESS || status == Status.LOADING
    }

}