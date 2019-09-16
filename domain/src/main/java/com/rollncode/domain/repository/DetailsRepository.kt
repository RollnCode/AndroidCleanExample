package com.rollncode.domain.repository

import androidx.lifecycle.LiveData
import com.rollncode.domain.entity.Details

interface DetailsRepository {
    fun getAll() : LiveData<List<Details>>
}