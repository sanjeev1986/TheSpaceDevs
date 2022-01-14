package com.sample.thespacedevs.feature.details

import com.google.android.gms.maps.model.LatLng
import com.sample.thespacedevs.services.launch.Results
import com.sample.thespacedevs.utils.UiStateMapper
import java.text.SimpleDateFormat
import java.util.*

internal data class LaunchDetailsUiStateModel(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val missionName: String? = null,
    val launchDescription: String? = null,
    val launchAgencyName: String = "",
    val launchDate: String = "",
    val launchCountDown: Long = 0,
    val launchCoordinates: LatLng? = null
)

internal class LoadingWidgetMapper : UiStateMapper<LaunchDetailsUiStateModel, Boolean> {
    override fun map(model: LaunchDetailsUiStateModel) = model.isLoading
}

internal class ErrorWidgetMapper : UiStateMapper<LaunchDetailsUiStateModel, Boolean> {
    override fun map(model: LaunchDetailsUiStateModel) = model.isError
}

internal class MissionNameWidgetMapper : UiStateMapper<LaunchDetailsUiStateModel, String> {
    override fun map(model: LaunchDetailsUiStateModel) = model.missionName.orEmpty()
}

internal class LaunchDescriptionNameWidgetMapper :
    UiStateMapper<LaunchDetailsUiStateModel, String> {
    override fun map(model: LaunchDetailsUiStateModel) = model.launchDescription.orEmpty()
}

internal class LaunchCountDownWidgetMapper :
    UiStateMapper<LaunchDetailsUiStateModel, Long> {
    override fun map(model: LaunchDetailsUiStateModel) = model.launchCountDown
}

internal class LaunchCoordinatesWidgetMapper :
    UiStateMapper<LaunchDetailsUiStateModel, LatLng?> {
    override fun map(model: LaunchDetailsUiStateModel) = model.launchCoordinates
}

internal class LaunchDateWidgetMapper :
    UiStateMapper<LaunchDetailsUiStateModel, String> {
    companion object {
        private var displayDateFormat = SimpleDateFormat("dd:HH:mm:ss", Locale.getDefault())
        private var windowStartDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    }

    override fun map(model: LaunchDetailsUiStateModel) =
        windowStartDateFormat.parse(model.launchDate)?.let { displayDateFormat.format(it) } ?: ""
}
