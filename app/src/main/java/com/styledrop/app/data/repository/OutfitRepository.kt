package com.styledrop.app.data.repository

import com.styledrop.app.data.local.dao.OutfitDao
import com.styledrop.app.data.local.entity.OutfitEntity
import kotlinx.coroutines.flow.Flow

class OutfitRepository(private val dao: OutfitDao) {
    fun getAllOutfits(): Flow<List<OutfitEntity>> = dao.getAllOutfits()
    suspend fun insertOutfit(outfit: OutfitEntity) = dao.insertOutfit(outfit)
    suspend fun updateOutfit(outfit: OutfitEntity) = dao.updateOutfit(outfit)
    suspend fun deleteOutfit(outfit: OutfitEntity) = dao.deleteOutfit(outfit)
    suspend fun updateFavourite(id: Int, value: Boolean) = dao.updateFavourite(id, value)
}
