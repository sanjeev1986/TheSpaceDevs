package com.sample.thespacedevs.services.launch

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pad(
    val name: String,
    val latitude: Double,
    val longitude: Double,
): Parcelable