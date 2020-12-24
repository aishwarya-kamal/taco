package com.candybytes.taco.ui.searchfood

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.candybytes.taco.data.local.FoodDao
import com.candybytes.taco.repository.DefaultRepository
import timber.log.Timber

class SearchFoodViewModel @ViewModelInject constructor(
    private val foodDao: FoodDao,
    private val repository: DefaultRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun getFilteredFoodList(query: String) = liveData {
        try {
            emit(repository.getFilteredFoodList(query))
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    val getFoodList = liveData {
        try {
            emit(repository.getFoodList())
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

}
