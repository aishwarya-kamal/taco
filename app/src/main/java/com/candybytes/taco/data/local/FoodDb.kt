package com.candybytes.taco.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Food


/**
 * Main database description.
 */
@Database(
    entities = [
        Category::class,
        Food::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FoodDb : RoomDatabase() {

    abstract fun foodDao(): FoodDao
}


//package com.candybytes.taco.data.local
//
//import androidx.room.Database
//import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
//import com.candybytes.taco.vo.Category
//import com.candybytes.taco.vo.Food
//import com.candybytes.taco.vo.RemoteKeys
//
//
///**
// * Main database description.
// */
//@Database(
//    entities = [
//        Category::class,
//        Food::class,
//        RemoteKeys::class],
//    version = 3,
//    exportSchema = true
//)
//@TypeConverters(Converters::class)
//abstract class FoodDb : RoomDatabase() {
//
//    abstract fun foodDao(): FoodDao
//    abstract fun remoteKeysDao(): RemoteKeysDao
//}
