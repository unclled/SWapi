package com.project.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.data.local.dao.CharacterDao
import com.project.data.local.entity.CharacterEntity
import com.project.data.local.utils.StringListConverter

@Database(entities =[CharacterEntity::class], version = 2, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class SwapiDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
}