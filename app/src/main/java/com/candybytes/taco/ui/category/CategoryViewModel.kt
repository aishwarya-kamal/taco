package com.candybytes.taco.ui.category

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.candybytes.taco.repository.DefaultRepository

// CategoryViewModel provides [Flow]<PagingData> of foods
class CategoryViewModel @ViewModelInject constructor(
    private val repository: DefaultRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun getCategoryFoodList(categoryId: Int) = Pager(
        PagingConfig(
            // Number of items per page
            pageSize = 20,
            enablePlaceholders = true,
            // Maximum number of food items PagedList should hold in memory
            maxSize = 200
        )
    ) {
        repository.getCategoryFoodList(categoryId)
    }.flow

}