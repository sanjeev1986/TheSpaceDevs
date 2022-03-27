package com.sample.thespacedevs.services.launch

import com.google.gson.annotations.SerializedName

data class Location(

    val id: Int,
    val url: String,
    val name: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("map_image")
    val mapImage: String,
    @SerializedName("total_launch_count")
    val totalLaunchCount: Int,
    @SerializedName("total_landing_count")
    val totalLandingCount: Int
)