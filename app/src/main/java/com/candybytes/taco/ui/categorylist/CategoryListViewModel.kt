package com.candybytes.taco.ui.categorylist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.candybytes.taco.repository.DefaultRepository
import com.candybytes.taco.vo.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class CategoryListViewModel @ViewModelInject constructor(
    private val repository: DefaultRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

//    private val _dataInserted = MutableLiveData<Boolean>()
//    val dataInserted: LiveData<Boolean> = _dataInserted


    init {
        viewModelScope.launch {
            repository.insertAllFood()
        }
    }

//    init {
//        viewModelScope.launch {
////            foodList()
//            if (repository.getFoodList().isEmpty()) {
//                Timber.d("** Inserting all food")
//                repository.insertAllFood()
//                Timber.d("** Inserted all food: done")
////                _dataInserted.value = true
//            } else {
////                _dataInserted.value = true
//            }
//        }
//    }

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
//
//    fun foodList() {
//        viewModelScope.launch {
//            repository.getFoodList()
//        }
//    }
}

