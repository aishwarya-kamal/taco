package com.candybytes.taco.ui.searchfood

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.candybytes.taco.data.local.FoodDao
import com.candybytes.taco.repository.DefaultRepository

class SearchFoodViewModel @ViewModelInject constructor(
    private val foodDao: FoodDao,
    private val repository: DefaultRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

//    fun getFilteredFoodList(query: String) = liveData {
//        try {
//            emit(repository.getFilteredFoodList(query))
//        } catch (e: Exception) {
//            Timber.e(e)
//        }
//    }

    fun getFilteredFoodList(query: String) = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 600
        )
    ) {
        repository.getFilteredFoodList(query)
    }.flow

//    val getFoodList = liveData {
//        try {
//            Timber.d("** vm ${repository.getAllFood()}")
//            emit(repository.getAllFood())
//        } catch (e: Exception) {
//            Timber.e(e)
//        }
//    }

    val allFood = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        repository.getAllFood()
    }.flow

}
