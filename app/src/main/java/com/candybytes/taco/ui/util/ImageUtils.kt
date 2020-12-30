package com.candybytes.taco.ui.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object ImageUtils {

    lateinit var currentImagePath: String

    @Throws(IOException::class)
    fun createFoodImageFile(context: Context): File? {

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
            .format(Date())
        val storageDirectory: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "IMG_${timeStamp}_",
            ".jpg",
            storageDirectory
        ).apply {
            currentImagePath = absolutePath
        }
    }
}