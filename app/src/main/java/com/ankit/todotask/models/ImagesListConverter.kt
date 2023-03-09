package com.ankit.todotask.models

import androidx.room.TypeConverter

class ImagesListConverter() {
    @TypeConverter
    fun storedStringToImages(value: String): List<String> {
        val langs = value.split("\\s*,\\s*")
        return langs
    }

    @TypeConverter
    fun imagesToStoredString(images: List<String>): String {
        var value = ""
        for (list in images) value += "$list,"
        return value
    }
}

