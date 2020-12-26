package com.candybytes.taco.ui.categorylist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.candybytes.taco.repository.DefaultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class CategoryListViewModel @ViewModelInject constructor(
    private val repository: DefaultRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        viewModelScope.launch {
            if (repository.getFoodList().isEmpty()) {
                Timber.d("** Inserting all food")
                repository.insertAllFood()
                Timber.d("** Inserted all food: done")
            }
        }
    }

    fun getCategoryFoodTotalNumber(categoryId: Int) = liveData(Dispatchers.IO) {
        try {
            Timber.d("** Category food - ${repository.getCategoryFoodTotalNumber(categoryId)}")
            emit(repository.getCategoryFoodTotalNumber(categoryId))
        } catch (e: Exception) {
            Timber.e(e)
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

