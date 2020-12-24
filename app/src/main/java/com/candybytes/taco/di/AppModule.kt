package com.candybytes.taco.di

import android.content.Context
import androidx.room.Room
import com.candybytes.taco.R
import com.candybytes.taco.data.local.FoodDao
import com.candybytes.taco.data.local.FoodDb
import com.candybytes.taco.data.remote.api.TacoService
import com.candybytes.taco.repository.DefaultRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    /**
     * Access food in db
     */
    @Provides
    fun provideFoodDao(foodDb: FoodDb): FoodDao {
        return foodDb.foodDao()
    }

//    @Provides
//    fun provideFoodImageDao(foodDb: FoodDb): FoodImageDao {
//        return foodDb.foodImageDao()
//    }

    /**
     * DB init
     */
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): FoodDb {
        return Room.databaseBuilder(
            context,
            FoodDb::class.java,
            "food"
        )
            .createFromAsset("food.db")
            .build()
    }

    /**
     * Provides [TacoService] REST API
     */
    @Provides
    fun provideTacoService(
        @ApplicationContext context: Context
    ): TacoService {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.taco_service_host))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TacoService::class.java)
    }

    /**
     * Provides [TacoService] REST API
     */
    @Provides
    fun provideRepository(
        tacoService: TacoService,
        foodDao: FoodDao
    ): DefaultRepository {
        return DefaultRepository(
            tacoService,
            foodDao
        )
    }
}
