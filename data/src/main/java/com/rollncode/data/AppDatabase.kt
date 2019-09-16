package com.rollncode.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rollncode.data.dao.DetailsDao
import com.rollncode.data.dto.DetailsEntity

@Database(
        version = 1,
        entities = [DetailsEntity::class]
         )
abstract class AppDatabase : RoomDatabase() {
    abstract val detailsDao: DetailsDao
}