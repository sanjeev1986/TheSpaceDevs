package com.sample.thespacedevs.feature.details

import android.os.CountDownTimer
import com.google.android.gms.maps.model.LatLng
import com.sample.thespacedevs.services.launch.Results
import com.sample.thespacedevs.utils.LaunchCountDownTimer
import com.sample.thespacedevs.utils.UiStateMapper
import java.text.SimpleDateFormat
import java.util.*

internal data class LaunchDetailsUiStateModel(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val result: Results? = null
)

internal data class LaunchDetails(
    val missionName: String = "",
    val launchDescription: String = "",
    val launchAgencyName: String = "",
    val launchDate: String = ""
)

internal data class LaunchTimer(
    val launchCountDown: Long = 0,
    val launchDate: String = ""
)

internal data class LaunchCoordinates(
    val latLng: LatLng? = null
) {
    fun isvalid() = latLng != null
}


internal class LaunchDetailsMapper : UiStateMapper<LaunchDetailsUiStateModel, LaunchDetails> {
    companion object {
        private var displayDateFormat = SimpleDateFormat("dd:HH:mm:ss", Locale.getDefault())
        private var windowStartDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    }

    override fun map(model: LaunchDetailsUiStateModel): LaunchDetails {
        return model.result?.let {
            LaunchDetails(
                missionName = it.name,
                launchAgencyName = it.mission?.name.orEmpty(),
                launchDescription = it.mission?.description.orEmpty(),
                launchDate = displayDateFormat.format(Date(windowStartDateFormat.parse(it.window_start).time))
            )
        } ?: LaunchDetails()
    }
}

internal class LaunchTimerMapper : UiStateMapper<LaunchDetailsUiStateModel, LaunchTimer> {
    companion object {
        private var windowStartDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    }

    override fun map(model: LaunchDetailsUiStateModel): LaunchTimer {
        return model.result?.let {
            LaunchTimer(
                launchCountDown = windowStartDateFormat.parse(it.window_start)?.time ?: 0L,
                launchDate = it.window_start
            )
        } ?: LaunchTimer()
    }
}

internal class LaunchCoordinatesMapper :
    UiStateMapper<LaunchDetailsUiStateModel, LaunchCoordinates> {
    override fun map(model: LaunchDetailsUiStateModel): LaunchCoordinates {
        return model.result?.let {
            LaunchCoordinates(
                latLng = LatLng(it.pad.latitude, it.pad.longitude)
            )
        } ?: LaunchCoordinates()
    }
}
