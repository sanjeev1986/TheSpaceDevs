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
    val launchAgencyName: String = ""
)

internal data class LaunchTimer(
    val launchCountDown: Long = 0,
    val launchDate: String = "",
    val timer: CountDownTimer? = null
)

internal data class LaunchCoordinates(
    val latLng: LatLng? = null
)


internal class LaunchDetailsMapper : UiStateMapper<LaunchDetailsUiStateModel, LaunchDetails> {
    override fun map(model: LaunchDetailsUiStateModel): LaunchDetails {
        return model.result?.let {
            LaunchDetails(
                missionName = it.name,
                launchAgencyName = it.mission?.name.orEmpty(),
                launchDescription = it.mission?.description.orEmpty()
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
                launchDate = it.window_start,
                timer = LaunchCountDownTimer(
                    windowStartDateFormat.parse(it.window_start)?.time ?: 0L, 1000L
                )
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
