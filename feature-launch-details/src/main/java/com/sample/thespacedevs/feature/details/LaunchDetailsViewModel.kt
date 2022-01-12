package com.sample.thespacedevs.feature.details

import androidx.lifecycle.*
import com.sample.repositories.launch.LaunchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

internal class LaunchDetailsViewModel(
    launchCoordinatesMapper: LaunchCoordinatesMapper = LaunchCoordinatesMapper(),
    launchDetailsMapper: LaunchDetailsMapper = LaunchDetailsMapper(),
    launchTimerMapper: LaunchTimerMapper = LaunchTimerMapper(),
    val repository: LaunchRepository
) : ViewModel() {

    private val state = MutableStateFlow(LaunchDetailsUiStateModel())

    val launchDetails: LiveData<LaunchDetails> =
        state.asLiveData().map { launchDetailsMapper.map(it) }.distinctUntilChanged()

    val launchTimers: LiveData<LaunchTimer> =
        state.asLiveData().map { launchTimerMapper.map(it) }.distinctUntilChanged()

    val launchCoordinates: LiveData<LaunchCoordinates> =
        state.asLiveData().map { launchCoordinatesMapper.map(it) }.distinctUntilChanged()


    fun fetchMissionDetails(id: String) {
        repository.getLaunchDetailsById(id)?.apply {
            state.value = LaunchDetailsUiStateModel(result = this)
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