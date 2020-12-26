package com.candybytes.taco.repository

import com.candybytes.taco.data.local.FoodDao
import com.candybytes.taco.data.remote.api.TacoService
import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Food
import timber.log.Timber
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val tacoService: TacoService,
    private val foodDao: FoodDao,
) : IRepository {

    override suspend fun getCategoryList(): List<Category> {
        return tacoService.getCategoryList()
    }

    override suspend fun getCategoryFoodTotalNumber(categoryId: Int): Int {
        var foodList = emptyList<Food>()
        try {
            foodList = getCategoryFoodList(categoryId)
            Timber.d("** getCategoryFoodList - ${foodList.size}")
        } catch (e: Exception) {
            Timber.e(e)
        }
        return foodList.size
    }

    override suspend fun getFoodList(): List<Food> {
        var foodList = emptyList<Food>()
        try {
            foodList = foodDao.getFoodList()
            Timber.d("** repo total food list size ${foodList.size}")
        } catch (e: Exception) {
            Timber.e(e)
        }
        return foodList
    }

    override suspend fun getFilteredFoodList(query: String): List<Food> {
        var filteredFoodList = emptyList<Food>()
        try {
            val searchQuery = "%$query%"
            filteredFoodList = foodDao.getFilteredFoodList(searchQuery)
            Timber.d("filtered list * $filteredFoodList")
        } catch (e: Exception) {
            Timber.e(e)
        }
        return filteredFoodList
    }

    override suspend fun getCategoryFoodList(categoryId: Int): List<Food> {
        var categoryFoodList = emptyList<Food>()
        try {
            categoryFoodList = foodDao.getCategoryFoodList(categoryId)
        } catch (e: Exception) {
            Timber.e(e)
        }
        return categoryFoodList
    }

    override suspend fun getFoodDetails(foodId: Int): Food {
        return foodDao.getFoodDetails(foodId)
    }

    suspend fun insertAllFood() {
        foodDao.insertAllFood(tacoService.getAllFoodList())
    }

    suspend fun update(imageUri: String, idPassed: Int) {
        Timber.d("** repo imageuri update - $imageUri")
        foodDao.update(imageUri, idPassed)
    }

}