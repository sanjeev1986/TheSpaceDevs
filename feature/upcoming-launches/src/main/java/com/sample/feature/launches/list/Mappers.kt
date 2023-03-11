package com.sample.feature.launches.list

import com.sample.base.UiStateMapper
import com.sample.feature.launches.services.Results

data class LaunchDisplayModel(
    val isLoading: Boolean = false,
    val results: List<Results> = emptyList(),
    val isError: Boolean = false,
)

data class LaunchListItem(
    val id: String,
    val missionName: String,
    val missionDescription: String,
)

class LaunchListMapper : UiStateMapper<LaunchDisplayModel, List<LaunchListItem>> {
    override fun map(model: LaunchDisplayModel): List<LaunchListItem> =
        model.results.map {
            LaunchListItem(
                id = it.id,
                missionName = it.name,
                missionDescription = it.mission?.description.orEmpty(),
            )
        }
}

class LoadingMapper : UiStateMapper<LaunchDisplayModel, Boolean> {
    override fun map(model: LaunchDisplayModel) = model.isLoading
}

class ErrorMapper : UiStateMapper<LaunchDisplayModel, Boolean> {
    override fun map(model: LaunchDisplayModel): Boolean = model.isError
}