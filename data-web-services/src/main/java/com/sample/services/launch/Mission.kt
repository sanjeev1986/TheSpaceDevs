package com.sample.services.launch

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mission(
    @SerializedName("name")
    var name: String = "Unknown",
    var description: String = "No Description available",
): Parcelable
