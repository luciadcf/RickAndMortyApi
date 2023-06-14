package es.luciadcf.rickandmorty.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CustomMediatorLiveData<Type> : Observer<Type> {

    //region Fields
    private val mediatorLiveData = MediatorLiveData<Type?>()
    private var actualSource: MutableLiveData<Type> = MutableLiveData()
    //endregion

    init {
        mediatorLiveData.addSource(actualSource, this)
    }

    //region Public methods
    fun changeSource(
        viewModelScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        source: LiveData<Type>
    ) {
        viewModelScope.launch(dispatcher) {
            removeSource(actualSource)
            addSource(source)
            actualSource = MutableLiveData<Type>().apply {
                value = source.value
            }
        }
    }

    fun setData(type: Type?) {
        actualSource.value = type
    }

    fun liveData() = mediatorLiveData as LiveData<Type>
    //endregion

    //region Protected methods
    private fun removeSource(sourceToRemove: LiveData<Type>) {
        mediatorLiveData.removeSource(sourceToRemove)
    }

    private fun addSource(sourceToAdd: LiveData<Type>) {
        mediatorLiveData.addSource(sourceToAdd, this)
    }
    //endregion

    //region override Observer<Type>
    override fun onChanged(value: Type) {
        mediatorLiveData.postValue(value)
    }
    //endregion
}
