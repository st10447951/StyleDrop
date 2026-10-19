package com.styledrop.app.data.repository

import com.styledrop.app.data.local.dao.WardrobeDao
import com.styledrop.app.data.local.entity.WardrobeItemEntity
import kotlinx.coroutines.flow.Flow

class WardrobeRepository(private val dao: WardrobeDao) {
    fun getAllItems(): Flow<List<WardrobeItemEntity>> = dao.getAllItems()
    suspend fun insertItem(item: WardrobeItemEntity) = dao.insertItem(item)
    suspend fun updateItem(item: WardrobeItemEntity) = dao.updateItem(item)
    suspend fun deleteItem(item: WardrobeItemEntity) = dao.deleteItem(item)
    suspend fun updateFavourite(id: Int, value: Boolean) = dao.updateFavourite(id, value)
}
