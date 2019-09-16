package com.rollncode.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Details(
    val title: String,
    val description: String,
    val address: String
) : Parcelable