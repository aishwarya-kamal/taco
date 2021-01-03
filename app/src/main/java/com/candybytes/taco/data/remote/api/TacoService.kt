package com.candybytes.taco.data.remote.api

import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Food
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * REST API access points for taco service
 * see https://taco-food-api.herokuapp.com/ for more information
 */
interface TacoService {

    /**
     * Request a specific category
     */
    @GET("api/v1/category/{categoryId}")
    suspend fun getCategory(@Path("categoryId") categoryId: Int): ArrayList<Category>

    /**
     * Request all available categories
     */
    @GET("api/v1/category")
    suspend fun getCategoryList(): List<Category>

    /**
     * Request all food
     */
    @GET("api/v1/food")
    suspend fun getAllFoodList(): List<Food>


    /**
     * Request all food from the specific category
     */
    @GET("api/v1/category/{categoryId}/food")
    fun getCategoryFoodList(
        @Path("categoryId") categoryId: Int
    ): List<Food>
}
