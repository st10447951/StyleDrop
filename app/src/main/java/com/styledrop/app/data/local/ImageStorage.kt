package com.styledrop.app.data.local

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object ImageStorage {
    fun saveImage(context: Context, uri: Uri): String? = try {
        val dir = File(context.filesDir, "wardrobe_images").apply { mkdirs() }
        val file = File(dir, "${UUID.randomUUID()}.jpg")
        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(file).use { output -> input.copyTo(output) }
        }
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    fun deleteImage(imagePath: String?) {
        if (imagePath.isNullOrBlank()) return
        runCatching { File(imagePath).takeIf { it.exists() }?.delete() }
    }
}
