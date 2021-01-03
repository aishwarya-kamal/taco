package com.candybytes.taco.vo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "categories")
@Parcelize
data class Category(

    /**
     * Category unique ID.
     */
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int = -1,

    /**
     * Category name.
     */
    @SerializedName("category")
    val name: String = ""
) : Parcelable
