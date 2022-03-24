package com.sample.feature.launches.details

import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.sample.feature.launches.list.LaunchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

internal class LaunchDetailsViewModel(
    val id: String,
    val repository: LaunchRepository
) : ViewModel() {

    private val state = MutableStateFlow(LaunchDetailViewState())
    val observableState = state.asLiveData()

    init {
        fetchMissionDetails()
    }

    private fun fetchMissionDetails() {
        state.value = state.value.copy(isLoading = true)
        repository.getLaunchDetailsById(id)?.apply {
            state.value = LaunchDetailViewState(
                isLoading = false,
                isError = false,
                missionName = mission?.name ?: "",
                launchDescription = mission?.description ?: "",
                launchAgencyName = name,
                launchDate = window_start,
                launchCoordinates = LatLng(pad.latitude, pad.longitude)
            )
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val id: String,
        private val repository: LaunchRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LaunchDetailsViewModel(id = id, repository = repository) as T
        }
    }
}