package com.sample.thespacedevs.services.spacecraft

import com.google.gson.annotations.SerializedName

data class SpaceCraft(
    val id: Int,
    val url: String,
    val name: String,
    @SerializedName("serial_number")
    val serialNumber: String,
    val status: Status,
    val description: String,
    @SerializedName("spacecraft_config")
    val spacecraftConfiguration: SpacecraftConfiguration,
)