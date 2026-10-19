package com.styledrop.app.data.local.dao

import androidx.room.*
import com.styledrop.app.data.local.entity.WardrobeItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WardrobeDao {
    @Query("SELECT * FROM wardrobe_items WHERE isDeleted = 0 ORDER BY id DESC")
    fun getAllItems(): Flow<List<WardrobeItemEntity>>

    @Query("SELECT * FROM wardrobe_items WHERE category = :category AND isDeleted = 0 ORDER BY id DESC")
    fun getItemsByCategory(category: String): Flow<List<WardrobeItemEntity>>

    @Query("SELECT * FROM wardrobe_items WHERE syncState = 'PENDING'")
    suspend fun getPendingItems(): List<WardrobeItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: WardrobeItemEntity)

    @Update
    suspend fun updateItem(item: WardrobeItemEntity)

    @Delete
    suspend fun deleteItem(item: WardrobeItemEntity)

    @Query("UPDATE wardrobe_items SET favourite = :favourite, updatedAt = :updatedAt, syncState = 'PENDING' WHERE id = :id")
    suspend fun updateFavourite(id: Int, favourite: Boolean, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE wardrobe_items SET syncState = 'SYNCED' WHERE clientId IN (:clientIds)")
    suspend fun markSynced(clientIds: List<String>)

    @Query("SELECT * FROM wardrobe_items WHERE clientId = :clientId LIMIT 1")
    suspend fun findByClientId(clientId: String): WardrobeItemEntity?
}
