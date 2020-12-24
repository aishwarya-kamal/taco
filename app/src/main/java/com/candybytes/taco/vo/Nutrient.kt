package com.candybytes.taco.vo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Nutrient(
    /**
     */
    @SerializedName("unit")
    val unit: String = "",

    /**
     *
     */
    @SerializedName("qty")
    val qty: String = ""
): Parcelable
