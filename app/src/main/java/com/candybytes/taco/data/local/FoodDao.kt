package com.candybytes.taco.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.candybytes.taco.vo.Food

/**
 * Interface for database access for Food related operations.
 */
@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsync(food: Food)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFood(foodList: List<Food>)

    @Query("UPDATE food SET imageUri=:imageUri WHERE id = :idPassed")
    suspend fun update(imageUri: String, idPassed: Int)

    @Query("SELECT * FROM food")
    fun getAllFood(): PagingSource<Int, Food>

    @Query("SELECT * FROM food WHERE description LIKE :searchQuery")
    fun getFilteredFoodList(searchQuery: String): PagingSource<Int, Food>

    @Query("SELECT * FROM food WHERE categoryId = :categoryId")
    fun getCategoryFoodList(categoryId: Int): PagingSource<Int, Food>

    @Query("SELECT * FROM food WHERE categoryId = :categoryId")
    fun getCategoryFoodListSize(categoryId: Int): List<Food>

    @Query("SELECT * FROM food WHERE id = :foodId")
    suspend fun getFoodDetails(foodId: Int): Food
}
