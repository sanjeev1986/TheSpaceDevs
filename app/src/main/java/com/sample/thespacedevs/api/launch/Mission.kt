package com.sample.thespacedevs.api.launch

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Mission(
    @SerializedName("name")
    var name: String = "Unknown",
    var description: String = "No Description available",
): Parcelable
