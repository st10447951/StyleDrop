package com.styledrop.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wardrobe_items")
data class WardrobeItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val clientId: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val category: String,
    val brand: String,
    val colour: String,
    val size: String,
    val imageUri: String? = null,
    val notes: String = "",
    val favourite: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis(),
    val syncState: String = "PENDING",
    val isDeleted: Boolean = false
)
