package com.candybytes.taco.ui.food

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.candybytes.taco.data.remote.api.TacoService
import com.candybytes.taco.data.local.FoodDao
import com.candybytes.taco.repository.DefaultRepository
import com.candybytes.taco.ui.util.NUTRIENTS_LIST
import com.candybytes.taco.vo.Nutrient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class FoodViewModel @ViewModelInject constructor(
    private val tacoService: TacoService,
    private val foodDao: FoodDao,
    private val repository: DefaultRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun getCategory(categoryId: Int) = liveData {
        try {
            Timber.d("********** Category name in vm- ${tacoService.getCategory(categoryId)}")
            val category = tacoService.getCategory(categoryId)
            emit(category[0])
        } catch (e: Exception) {
            Timber.d("********** Exeception ${e.message}")
            Timber.e(e)
        }
    }

    fun getFoodDetails(foodId: Int) = liveData(Dispatchers.IO) {
        try {
            emit(repository.getFoodDetails(foodId))
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}