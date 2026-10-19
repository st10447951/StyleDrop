package com.styledrop.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.styledrop.app.data.local.dao.OutfitDao
import com.styledrop.app.data.local.dao.WardrobeDao
import com.styledrop.app.data.local.dao.WishlistDao
import com.styledrop.app.data.local.entity.OutfitEntity
import com.styledrop.app.data.local.entity.WardrobeItemEntity
import com.styledrop.app.data.local.entity.WishlistItemEntity

@Database(
    entities = [WardrobeItemEntity::class, OutfitEntity::class, WishlistItemEntity::class],
    version = 4,
    exportSchema = false
)
abstract class StyleDropDatabase : RoomDatabase() {
    abstract fun wardrobeDao(): WardrobeDao
    abstract fun outfitDao(): OutfitDao
    abstract fun wishlistDao(): WishlistDao

    companion object {
        @Volatile private var INSTANCE: StyleDropDatabase? = null

        fun getDatabase(context: Context): StyleDropDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    StyleDropDatabase::class.java,
                    "styledrop_database"
                ).fallbackToDestructiveMigration(true).build().also { INSTANCE = it }
            }
    }
}
