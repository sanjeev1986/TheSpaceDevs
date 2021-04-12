package com.sample.thespacedevs.api.launch

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Results(
    val id:String,
    val name: String,
    val window_start: String,
    val mission: Mission?,
    val pad: Pad
) : Parcelable