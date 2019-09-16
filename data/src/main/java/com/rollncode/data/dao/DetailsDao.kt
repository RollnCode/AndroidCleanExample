package com.rollncode.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.rollncode.data.dto.DetailsEntity

@Dao
interface DetailsDao : BaseDao<DetailsEntity> {

    companion object {
        const val TABLE_NAME = "details"
    }

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): LiveData<List<DetailsEntity>>
}