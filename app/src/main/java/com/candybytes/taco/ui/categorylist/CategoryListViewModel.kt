package com.candybytes.taco.ui.categorylist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.candybytes.taco.repository.DefaultRepository
import com.candybytes.taco.ui.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class CategoryListViewModel @ViewModelInject constructor(
    private val repository: DefaultRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.insertAllFood()
            Timber.d("Data inserted...")
        }
    }

    /*
    * LiveData builder runs the coroutine on IO thread when it’s observed and exposes/emits the
    * results through an immutable LiveData which are caught by its observers
    * */
    val getCategoryList = liveData(Dispatchers.IO) {
        // Emit loading
        emit(Resource.loading(null))

        // Emits the results (internally calls LiveData.setValue())
        emit(repository.getCategoryList())
    }


    // Gets the category food list size
    fun getCategoryFoodListSize(categoryId: Int): Int {
        return try {
            repository.getCategoryFoodListSize(categoryId)
        } catch (e: Exception) {
            Timber.e(e)
            -100
        }
    }
}
