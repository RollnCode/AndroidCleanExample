package com.rollncode.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.rollncode.data.dao.DetailsDao
import com.rollncode.data.dto.DetailsEntity
import com.rollncode.domain.entity.Details
import com.rollncode.domain.repository.DetailsRepository
import java.io.FileReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsDataRepository @Inject constructor(private val detailsDao: DetailsDao) : DetailsRepository {

    override fun getAll(): LiveData<List<Details>> {
        val details = Gson().fromJson<List<DetailsEntity>>(JsonReader(FileReader("details.json")), Array<DetailsEntity>::class.java)
        detailsDao.save(details)
        return Transformations.map(detailsDao.getAll()) { entity ->
            entity.map { DetailsEntity.Mapper.transform(it) }
        }
    }
}