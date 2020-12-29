package com.candybytes.taco.ui.categorylist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.candybytes.taco.repository.DefaultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class CategoryListViewModel @ViewModelInject constructor(
    private val repository: DefaultRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataInserted = MutableLiveData<Boolean>()
    val dataInserted: LiveData<Boolean> = _dataInserted

    init {
        viewModelScope.launch {
            if (dataInserted.value == false) {
                repository.insertAllFood()
                Timber.d("** Data inserted...")
                _dataInserted.value = true
            } else {
                Timber.d("** Data already inserted...")
            }
        }
    }

    val getCategoryList = liveData(Dispatchers.IO) {
        try {
            emit(repository.getCategoryList())
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}

