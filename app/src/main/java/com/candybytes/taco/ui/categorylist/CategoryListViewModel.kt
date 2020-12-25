package com.candybytes.taco.ui.categorylist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.candybytes.taco.data.remote.api.TacoService
import com.candybytes.taco.data.local.FoodDao
import com.candybytes.taco.repository.DefaultRepository
import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class CategoryListViewModel @ViewModelInject constructor(
    private val repository: DefaultRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        viewModelScope.launch {
            Timber.d("** Inserting all food")
            repository.insertAllFood()
            Timber.d("** Inserted all food: done")
        }
    }

    fun getCategoryFoodTotalNumber(categoryId: Int) = liveData(Dispatchers.IO) {
        try {
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

