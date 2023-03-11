package com.sample.feature.launches.services

import com.google.gson.annotations.SerializedName

data class Mission(
    @SerializedName("name")
    var name: String = "Unknown",
    var description: String = "No Description available",
)