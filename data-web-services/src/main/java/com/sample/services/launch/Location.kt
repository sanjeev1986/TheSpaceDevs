package com.sample.services.launch

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(

    val id: Int,
    val url: String,
    val name: String,
    val country_code: String,
    val map_image: String,
    val total_launch_count: Int,
    val total_landing_count: Int
):Parcelable