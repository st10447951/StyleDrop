package com.styledrop.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "outfits")
data class OutfitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val clientId: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val topId: Int? = null,
    val bottomId: Int? = null,
    val shoesId: Int? = null,
    val outerwearId: Int? = null,
    val accessoryId: Int? = null,
    val favourite: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis(),
    val syncState: String = "PENDING",
    val isDeleted: Boolean = false
)
