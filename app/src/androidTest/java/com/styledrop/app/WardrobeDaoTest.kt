package com.styledrop.app

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.styledrop.app.data.local.StyleDropDatabase
import com.styledrop.app.data.local.dao.WardrobeDao
import com.styledrop.app.data.local.entity.WardrobeItemEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class WardrobeDaoTest {
    private lateinit var db: StyleDropDatabase
    private lateinit var wardrobeDao: WardrobeDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, StyleDropDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        wardrobeDao = db.wardrobeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeItemAndReadInList() = runBlocking {
        val item = WardrobeItemEntity(
            name = "Blue Denim Jacket",
            category = "Outerwear",
            brand = "Levi's",
            colour = "Blue",
            size = "M"
        )
        wardrobeDao.insertItem(item)
        val items = wardrobeDao.getAllItems().first()
        assertEquals(1, items.size)
        assertEquals("Blue Denim Jacket", items[0].name)
        assertEquals("Outerwear", items[0].category)
    }

    @Test
    @Throws(Exception::class)
    fun updateFavouriteStatus() = runBlocking {
        val item = WardrobeItemEntity(
            name = "Black Boots",
            category = "Shoes",
            brand = "Dr. Martens",
            colour = "Black",
            size = "9"
        )
        wardrobeDao.insertItem(item)
        val inserted = wardrobeDao.getAllItems().first()[0]
        
        wardrobeDao.updateFavourite(inserted.id, true)
        val updated = wardrobeDao.getAllItems().first()[0]
        assertTrue(updated.favourite)
    }
}
