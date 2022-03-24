package com.sample.feature.launches.list

import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.sample.thespacedevs.services.launch.Results
import com.sample.thespacedevs.utils.UiStateMapper

data class LaunchDisplayModel(
    val isLoading: Boolean = false,
    val results: List<Results> = emptyList(),
    val isError: Boolean = false
)

data class LaunchListItem(
    val id: String,
    val missionName: String,
    val missionDescription: String
)

class LaunchListMapper : UiStateMapper<LaunchDisplayModel, List<LaunchListItem>> {
    override fun map(model: LaunchDisplayModel): List<LaunchListItem> =
        model.results.map {
            LaunchListItem(
                id = it.id,
                missionName = it.name,
                missionDescription = it.mission?.description.orEmpty()
            )
        }


}

class LoadingMapper : UiStateMapper<LaunchDisplayModel, SwipeRefreshState> {
    override fun map(model: LaunchDisplayModel): SwipeRefreshState =
        SwipeRefreshState(model.isLoading)
}

class ErrorMapper : UiStateMapper<LaunchDisplayModel, Boolean> {
    override fun map(model: LaunchDisplayModel): Boolean = model.isError
}