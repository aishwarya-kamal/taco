package com.candybytes.taco.repository

import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Food

interface IRepository {
    suspend fun getCategoryList(): List<Category>
    suspend fun getCategoryFoodTotalNumber(categoryId: Int) : Int
    suspend fun getFoodList(): List<Food>
    suspend fun getFilteredFoodList(query: String): List<Food>
    suspend fun getCategoryFoodList(categoryId: Int): List<Food>
    suspend fun getFoodDetails(foodId: Int): Food
}