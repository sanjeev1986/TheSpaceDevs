package com.sample.thespacedevs.services.launch

import android.os.Parcelable
import com.sample.thespacedevs.services.launch.Mission
import com.sample.thespacedevs.services.launch.Pad
import kotlinx.parcelize.Parcelize

@Parcelize
data class Results(
    val id:String,
    val name: String,
    val window_start: String,
    val mission: Mission?,
    val pad: Pad
) : Parcelable