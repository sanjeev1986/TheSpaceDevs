package com.sample.thespacedevs.feature.details

import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

internal class LaunchDetailsViewModel(
    loadingWidgetMapper: LoadingWidgetMapper = LoadingWidgetMapper(),
    errorWidgetMapper: ErrorWidgetMapper = ErrorWidgetMapper(),
    missionNameWidgetMapper: MissionNameWidgetMapper = MissionNameWidgetMapper(),
    launchDescriptionNameWidgetMapper: LaunchDescriptionNameWidgetMapper = LaunchDescriptionNameWidgetMapper(),
    launchCountDownWidgetMapper: LaunchCountDownWidgetMapper = LaunchCountDownWidgetMapper(),
    launchCoordinatesWidgetMapper: LaunchCoordinatesWidgetMapper = LaunchCoordinatesWidgetMapper(),
    launchDateWidgetMapper: LaunchDateWidgetMapper = LaunchDateWidgetMapper(),
    val repository: LaunchRepository
) : ViewModel() {

    private val state = MutableStateFlow(LaunchDetailsUiStateModel())

    val loadingState: LiveData<Boolean> =
        state.asLiveData().map { loadingWidgetMapper.map(it) }.distinctUntilChanged()

    val errorState: LiveData<Boolean> =
        state.asLiveData().map { errorWidgetMapper.map(it) }.distinctUntilChanged()

    val missionName: LiveData<String> =
        state.asLiveData().map { missionNameWidgetMapper.map(it) }.distinctUntilChanged()

    val launchDescription: LiveData<String> =
        state.asLiveData().map { launchDescriptionNameWidgetMapper.map(it) }.distinctUntilChanged()

    val launchCountDown: LiveData<Long> =
        state.asLiveData().map { launchCountDownWidgetMapper.map(it) }.distinctUntilChanged()

    val launchCoordinates: LiveData<LatLng?> =
        state.asLiveData().map { launchCoordinatesWidgetMapper.map(it) }.distinctUntilChanged()

    val launchDate: LiveData<String> =
        state.asLiveData().map { launchDateWidgetMapper.map(it) }.distinctUntilChanged()



    fun fetchMissionDetails(id: String) {
        repository.getLaunchDetailsById(id)?.apply {
            state.value = LaunchDetailsUiStateModel(
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
    internal class VMFactory @Inject constructor(
        private val repository: LaunchRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LaunchDetailsViewModel(repository = repository) as T
        }
    }
}