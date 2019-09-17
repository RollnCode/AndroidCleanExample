package com.rollncode.data.dto

import android.os.Parcelable
import androidx.room.Entity
import com.rollncode.data.dao.DetailsDao
import com.rollncode.domain.entity.Detail
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = DetailsDao.TABLE_NAME
)
data class DetailsEntity(
    val title: String,
    val description: String,
    val address: String
) : Parcelable {

    object Mapper {
        fun transform(model: Detail) = DetailsEntity(
            model.title,
            model.description,
            model.address
        )

        fun transform(entity: DetailsEntity) = Detail(
            entity.title,
            entity.description,
            entity.address
        )
    }
}