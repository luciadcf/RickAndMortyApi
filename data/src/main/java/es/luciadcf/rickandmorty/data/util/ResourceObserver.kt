package es.luciadcf.rickandmorty.data.util

import android.util.Log
import androidx.lifecycle.Observer

abstract class ResourceObserver<T> : Observer<Resource<T>?> {

    override fun onChanged(value: Resource<T>?) {
        value?.let {
            when {
                it.isSuccess() -> {
                    onLoading(false)
                    onSuccess(it.data)
                }
                it.isError() -> {
                    onLoading(false)
                    it.error?.let { error ->
                        onError(error)
                    }
                }
                else -> {
                    onLoading(true)
                }
            }
        }
    }

    abstract fun onSuccess(response: T?)

    open fun onError(error: CustomError) {
        Log.d("ERROR", error.serverErrorMessage ?: "")
    }

    open fun onLoading(loading: Boolean) {
        // no-op
    }

}