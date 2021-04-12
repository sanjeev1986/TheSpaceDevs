package com.sample.thespacedevs.api.launch

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pad(
    val name: String,
    val latitude: Double,
    val longitude: Double,
): Parcelable