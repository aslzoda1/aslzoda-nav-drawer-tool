package com.example.aslzoda_nav_drawer_toolbar.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object ImageUtils {
    fun copyToInternalStorage(context: Context, uri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val file = File(context.filesDir, "profile_image.jpg")

        FileOutputStream(file).use { output ->
            inputStream.copyTo(output)
        }

        inputStream.close()
        return file.absolutePath
    }
    fun saveToInternal(context: Context, uri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalStateException("Cannot open input stream")

        val file = File(context.filesDir, "profile_image.jpg")

        FileOutputStream(file).use { output ->
            inputStream.copyTo(output)
        }

        inputStream.close()
        return file.absolutePath
    }
}