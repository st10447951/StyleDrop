package com.styledrop.app.data.local.dao

import androidx.room.*
import com.styledrop.app.data.local.entity.WishlistItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {
    @Query("SELECT * FROM wishlist_items ORDER BY productId DESC")
    fun getAllWishlistItems(): Flow<List<WishlistItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlistItem(item: WishlistItemEntity)

    @Delete
    suspend fun deleteWishlistItem(item: WishlistItemEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM wishlist_items WHERE productId = :productId LIMIT 1)")
    suspend fun isWishlisted(productId: String): Boolean
}
