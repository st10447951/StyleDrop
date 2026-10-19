package com.styledrop.app.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.styledrop.app.`data`.local.dao.OutfitDao
import com.styledrop.app.`data`.local.dao.OutfitDao_Impl
import com.styledrop.app.`data`.local.dao.WardrobeDao
import com.styledrop.app.`data`.local.dao.WardrobeDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class StyleDropDatabase_Impl : StyleDropDatabase() {
  private val _wardrobeDao: Lazy<WardrobeDao> = lazy {
    WardrobeDao_Impl(this)
  }

  private val _outfitDao: Lazy<OutfitDao> = lazy {
    OutfitDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(3, "7632b88df8879f5dad40800d6f0e6480", "764f1c672e8dbca6b31ebae7a9ab3caf") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `wardrobe_items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `clientId` TEXT NOT NULL, `name` TEXT NOT NULL, `category` TEXT NOT NULL, `brand` TEXT NOT NULL, `colour` TEXT NOT NULL, `size` TEXT NOT NULL, `imageUri` TEXT, `notes` TEXT NOT NULL, `favourite` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `syncState` TEXT NOT NULL, `isDeleted` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `outfits` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `clientId` TEXT NOT NULL, `name` TEXT NOT NULL, `topId` INTEGER, `bottomId` INTEGER, `shoesId` INTEGER, `outerwearId` INTEGER, `accessoryId` INTEGER, `favourite` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `syncState` TEXT NOT NULL, `isDeleted` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7632b88df8879f5dad40800d6f0e6480')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `wardrobe_items`")
        connection.execSQL("DROP TABLE IF EXISTS `outfits`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsWardrobeItems: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsWardrobeItems.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWardrobeItems.put("clientId", TableInfo.Column("clientId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWardrobeItems.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWardrobeItems.put("category", TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWardrobeItems.put("brand", TableInfo.Column("brand", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWardrobeItems.put("colour", TableInfo.Column("colour", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWardrobeItems.put("size", TableInfo.Column("size", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWardrobeItems.put("imageUri", TableInfo.Column("imageUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWardrobeItems.put("notes", TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWardrobeItems.put("favourite", TableInfo.Column("favourite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWardrobeItems.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWardrobeItems.put("syncState", TableInfo.Column("syncState", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWardrobeItems.put("isDeleted", TableInfo.Column("isDeleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysWardrobeItems: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesWardrobeItems: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoWardrobeItems: TableInfo = TableInfo("wardrobe_items", _columnsWardrobeItems, _foreignKeysWardrobeItems, _indicesWardrobeItems)
        val _existingWardrobeItems: TableInfo = read(connection, "wardrobe_items")
        if (!_infoWardrobeItems.equals(_existingWardrobeItems)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |wardrobe_items(com.styledrop.app.data.local.entity.WardrobeItemEntity).
              | Expected:
              |""".trimMargin() + _infoWardrobeItems + """
              |
              | Found:
              |""".trimMargin() + _existingWardrobeItems)
        }
        val _columnsOutfits: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsOutfits.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOutfits.put("clientId", TableInfo.Column("clientId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOutfits.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOutfits.put("topId", TableInfo.Column("topId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOutfits.put("bottomId", TableInfo.Column("bottomId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOutfits.put("shoesId", TableInfo.Column("shoesId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOutfits.put("outerwearId", TableInfo.Column("outerwearId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOutfits.put("accessoryId", TableInfo.Column("accessoryId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOutfits.put("favourite", TableInfo.Column("favourite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOutfits.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOutfits.put("syncState", TableInfo.Column("syncState", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOutfits.put("isDeleted", TableInfo.Column("isDeleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysOutfits: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesOutfits: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoOutfits: TableInfo = TableInfo("outfits", _columnsOutfits, _foreignKeysOutfits, _indicesOutfits)
        val _existingOutfits: TableInfo = read(connection, "outfits")
        if (!_infoOutfits.equals(_existingOutfits)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |outfits(com.styledrop.app.data.local.entity.OutfitEntity).
              | Expected:
              |""".trimMargin() + _infoOutfits + """
              |
              | Found:
              |""".trimMargin() + _existingOutfits)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "wardrobe_items", "outfits")
  }

  public override fun clearAllTables() {
    super.performClear(false, "wardrobe_items", "outfits")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(WardrobeDao::class, WardrobeDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(OutfitDao::class, OutfitDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun wardrobeDao(): WardrobeDao = _wardrobeDao.value

  public override fun outfitDao(): OutfitDao = _outfitDao.value
}
