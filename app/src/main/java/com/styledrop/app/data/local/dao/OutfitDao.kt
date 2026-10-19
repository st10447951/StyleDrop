package com.styledrop.app.data.local.dao

import androidx.room.*
import com.styledrop.app.data.local.entity.OutfitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OutfitDao {
    @Query("SELECT * FROM outfits WHERE isDeleted = 0 ORDER BY id DESC")
    fun getAllOutfits(): Flow<List<OutfitEntity>>

    @Query("SELECT * FROM outfits WHERE syncState = 'PENDING'")
    suspend fun getPendingOutfits(): List<OutfitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOutfit(outfit: OutfitEntity)

    @Update
    suspend fun updateOutfit(outfit: OutfitEntity)

    @Delete
    suspend fun deleteOutfit(outfit: OutfitEntity)

    @Query("UPDATE outfits SET favourite = :favourite, updatedAt = :updatedAt, syncState = 'PENDING' WHERE id = :id")
    suspend fun updateFavourite(id: Int, favourite: Boolean, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE outfits SET syncState = 'SYNCED' WHERE clientId IN (:clientIds)")
    suspend fun markSynced(clientIds: List<String>)

    @Query("SELECT * FROM outfits WHERE clientId = :clientId LIMIT 1")
    suspend fun findByClientId(clientId: String): OutfitEntity?
}
