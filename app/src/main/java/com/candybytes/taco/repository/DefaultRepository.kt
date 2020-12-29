package com.candybytes.taco.repository

import androidx.paging.PagingSource
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
        return try {
            val foods = foodDao.getCategoryFoodList(categoryId)
            foods.size
        } catch (e: Exception) {
            Timber.e(e)
            -1
        }
    }

    override fun getAllFood(): PagingSource<Int, Food> {
        return foodDao.getAllFood()
    }

    override fun getFilteredFoodList(query: String): PagingSource<Int, Food> {
        val searchQuery = "%$query%"
        return foodDao.getFilteredFoodList(searchQuery)
    }

//    override suspend fun getFilteredFoodList(query: String): List<Food> {
//        return try {
//            val searchQuery = "%$query%"
//            foodDao.getFilteredFoodList(searchQuery)
//        } catch (e: Exception) {
//            Timber.e(e)
//            emptyList()
//        }
//    }

    override suspend fun getCategoryFoodList(categoryId: Int): List<Food> {
        return try {
            foodDao.getCategoryFoodList(categoryId)
        } catch (e: Exception) {
            Timber.e(e)
            emptyList()
        }
    }

    override suspend fun getFoodDetails(foodId: Int): Food {
        return foodDao.getFoodDetails(foodId)
    }

    override suspend fun insertAllFood() {
        foodDao.insertAllFood(tacoService.getAllFoodList())
    }

    override suspend fun update(imageUri: String, idPassed: Int) {
        foodDao.update(imageUri, idPassed)
    }

}