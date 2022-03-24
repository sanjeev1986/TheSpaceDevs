package com.sample.feature.launches.details

import com.google.android.gms.maps.model.LatLng

data class LaunchDetailViewState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val missionName: String = "",
    val launchDescription: String = "",
    val launchAgencyName: String = "",
    val launchDate: String = "",
    val launchCountDown: Long = 0,
    val launchCoordinates: LatLng? = null
)