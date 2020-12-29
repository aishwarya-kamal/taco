package com.candybytes.taco.ui.category

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.candybytes.taco.repository.DefaultRepository

class CategoryViewModel @ViewModelInject constructor(
    private val repository: DefaultRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

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