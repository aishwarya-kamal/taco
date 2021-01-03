package com.candybytes.taco.ui.food

import android.net.Uri
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.candybytes.taco.data.remote.api.TacoService
import com.candybytes.taco.repository.DefaultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class FoodViewModel @ViewModelInject constructor(
    private val tacoService: TacoService,
    private val repository: DefaultRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri> = _imageUri

    fun setImageUri(image: Uri) {
        _imageUri.value = image
    }

    // Get category
    fun getCategory(categoryId: Int) = liveData {
        try {
            Timber.d("*** Category name - ${tacoService.getCategory(categoryId)}")
            val category = tacoService.getCategory(categoryId)
            emit(category[0])
        } catch (e: Exception) {
            Timber.d("*** Exeception ${e.message}")
            Timber.e(e)
        }
    }

    // Get food details
    fun getFoodDetails(foodId: Int) = liveData(Dispatchers.IO) {
        try {
            emit(repository.getFoodDetails(foodId))
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    // To update a food item with imageUri
    fun update(uri: String, idPassed: Int) {
        viewModelScope.launch {
            Timber.d("*** Imageuri update - $uri")
            repository.update(uri, idPassed)
        }
    }
}