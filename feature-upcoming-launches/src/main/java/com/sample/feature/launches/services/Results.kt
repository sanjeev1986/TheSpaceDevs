package com.sample.feature.launches.services

import com.google.gson.annotations.SerializedName

data class Results(
    val id:String,
    val name: String,
    @SerializedName("window_start")
    val windowStart: String,
    val mission: Mission?,
    val pad: Pad
)