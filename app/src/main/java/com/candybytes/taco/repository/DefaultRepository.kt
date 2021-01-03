package com.candybytes.taco.repository

import androidx.paging.PagingSource
import com.candybytes.taco.data.local.FoodDao
import com.candybytes.taco.data.remote.api.TacoService
import com.candybytes.taco.ui.util.Resource
import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Food
import timber.log.Timber
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val tacoService: TacoService,
    private val foodDao: FoodDao,
) : IRepository {

    // Gets list of categories
    override suspend fun getCategoryList(): Resource<List<Category>> {
        return try {
            Resource.success(tacoService.getCategoryList())
        } catch (exception: Exception) {
            return Resource.error(exception.message!!)
        }
    }

    // Gets all food from database
    override fun getAllFood(): PagingSource<Int, Food> {
        return foodDao.getAllFood()
    }

    // Gets filtered food list based on query string
    override fun getFilteredFoodList(query: String): PagingSource<Int, Food> {
        val searchQuery = "%$query%"
        return foodDao.getFilteredFoodList(searchQuery)
    }

    // Gets category food list from database
    override fun getCategoryFoodList(categoryId: Int): PagingSource<Int, Food> {
        return foodDao.getCategoryFoodList(categoryId)
    }

    // Gets food details from database
    override suspend fun getFoodDetails(foodId: Int): Food {
        return foodDao.getFoodDetails(foodId)
    }

    // Inserts all food into the database
    override suspend fun insertAllFood() {
        foodDao.insertAllFood(tacoService.getAllFoodList())
    }

    // Updates the food
    override suspend fun update(imageUri: String, idPassed: Int) {
        foodDao.update(imageUri, idPassed)
    }

    // Gets category food list size
    override fun getCategoryFoodListSize(categoryId: Int): Int {
        return try {
            foodDao.getCategoryFoodListSize(categoryId).size
        } catch (e: Exception) {
            Timber.e(e)
            -1
        }
    }

}