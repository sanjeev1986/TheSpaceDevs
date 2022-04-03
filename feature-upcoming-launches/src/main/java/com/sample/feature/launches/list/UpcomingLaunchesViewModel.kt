package com.sample.feature.launches.list

import androidx.lifecycle.*
import com.sample.base.IOResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingLaunchesViewModel @Inject constructor(
    launchListMapper: LaunchListMapper = LaunchListMapper(),
    loadingMapper: LoadingMapper = LoadingMapper(),
    errorMapper: ErrorMapper = ErrorMapper(),
    private val repository: LaunchRepository
) : ViewModel() {

    private val state = MutableStateFlow(LaunchDisplayModel())
    val upcomingLaunches = state.asLiveData().map { launchListMapper.map(it) }
    val loading = state.asLiveData().map { loadingMapper.map(it) }
    val error = state.asLiveData().map { errorMapper.map(it) }

    init {
        fetchUpcomingLaunches()
    }

    fun fetchUpcomingLaunches(refresh: Boolean = false) {
        state.update { it.copy(isLoading = true, isError = false) }
        viewModelScope.launch {
            when (val result = repository.getUpcomingLaunches(10, refresh)) {
                is IOResult.Success ->
                    state.update {
                        it.copy(
                            isLoading = false,
                            results = result.result,
                            isError = false
                        )
                    }
                is IOResult.Failure ->
                    state.update { it.copy(isLoading = false, isError = true) }
            }
        }
    }

    fun search(name: String) {
        viewModelScope.launch {
            when (val result = repository.getUpcomingLaunches(10, false)) {
                is IOResult.Success -> {
                    state.update { launchDisplayModel ->
                        launchDisplayModel.copy(
                            isLoading = false,
                            isError = false,
                            results = result.result.filter {
                                it.name.startsWith(
                                    name,
                                    ignoreCase = true
                                )
                            })
                    }
                }
                is IOResult.Failure -> {
                    state.update { it.copy(isLoading = false, isError = true) }
                }
            }
        }
    }

    fun dismissError(){
        state.update { it.copy(isLoading = false, isError = false) }
    }
}