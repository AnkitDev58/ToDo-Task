package com.ankit.todotask.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "products")
data class Product(
    var brand: String,
    var category: String,
    var description: String,
    var discountPercentage: Double,
    @PrimaryKey var id: Int,
    @TypeConverters(ImagesListConverter::class)
    var images: List<String>?,
    var price: Int,
    var rating: Double,
    var stock: Int,
    var thumbnail: String,
    var title: String,
    var isComplete:Boolean=false
)