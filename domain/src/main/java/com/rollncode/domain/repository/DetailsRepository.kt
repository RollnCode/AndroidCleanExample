package com.rollncode.domain.repository

import androidx.lifecycle.LiveData
import com.rollncode.domain.entity.Detail

interface DetailsRepository {
    fun getAll() : LiveData<List<Detail>>
}