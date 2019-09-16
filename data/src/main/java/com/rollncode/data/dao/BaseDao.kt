package com.rollncode.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
abstract interface BaseDao<T : Any> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(value: T?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(list: List<T>?)

    @Update
    fun update(value: T?)
}