package com.styledrop.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist_items")
data class WishlistItemEntity(
    @PrimaryKey
    val productId: String,
    val name: String,
    val title: String,
    val brand: String,
    val price: Double,
    val imageUrl: String?
)
