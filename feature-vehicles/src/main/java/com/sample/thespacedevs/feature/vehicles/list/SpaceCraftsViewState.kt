package com.sample.thespacedevs.feature.vehicles.list

internal data class SpaceCraftsViewState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val spaceCrafts: List<SpaceCraftViewData> = emptyList()
)

internal data class SpaceCraftViewData(
    val imageUrl: String?,
    val name: String,
    val status: String,
    val description: String
)