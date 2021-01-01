package com.candybytes.taco.repository

import androidx.paging.PagingSource
import com.candybytes.taco.ui.util.Resource
import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Food

interface IRepository {
    suspend fun getCategoryList(): Resource<List<Category>>
    fun getCategoryFoodListSize(categoryId: Int) : Int
    fun getAllFood(): PagingSource<Int, Food>
    fun getFilteredFoodList(query: String): PagingSource<Int, Food>
    fun getCategoryFoodList(categoryId: Int): PagingSource<Int, Food>
    suspend fun getFoodDetails(foodId: Int): Food
    suspend fun update(imageUri: String, idPassed: Int)
    suspend fun insertAllFood()
}