package com.candybytes.taco.data.local

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

    @Query("SELECT * FROM food")
    suspend fun getFoodList(): List<Food>

    @Query("SELECT * FROM food WHERE description LIKE :searchQuery")
    suspend fun getFilteredFoodList(searchQuery: String): List<Food>

//    @Query("SELECT * FROM food WHERE categoryId=:categoryId")
//    suspend fun getCategoryFoodList(categoryId: Int): List<Food>

    @Query("SELECT * FROM food WHERE categoryId=:categoryId")
     fun getCategoryFoodList(categoryId: Int): List<Food>

    @Query("SELECT * FROM food WHERE id=:foodId")
    suspend fun getFoodDetails(foodId: Int): Food
}
