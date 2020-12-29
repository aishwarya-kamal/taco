package com.candybytes.taco.ui.category

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.candybytes.taco.data.remote.api.TacoService
import com.candybytes.taco.data.local.FoodDao
import com.candybytes.taco.repository.DefaultRepository
import timber.log.Timber

class CategoryViewModel @ViewModelInject constructor(
    private val repository: DefaultRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

//    fun getCategoryFoodList(categoryId: Int) = liveData {
//        try {
//            emit(repository.getCategoryFoodList(categoryId))
//        } catch (e: Exception) {
//            Timber.e(e)
//        }
//    }

    fun getCategoryFoodList(categoryId: Int) = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        repository.getCategoryFoodList(categoryId)
    }.flow

}