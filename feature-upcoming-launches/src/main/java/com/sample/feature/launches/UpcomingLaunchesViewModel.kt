package com.sample.feature.launches

import androidx.lifecycle.*
import com.sample.repositories.launch.LaunchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class UpcomingLaunchesViewModel(
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
        fetchUpcomingLaunches(true)
    }

    fun fetchUpcomingLaunches(refresh: Boolean) {
        state.update { it.copy(isLoading = true, isError = false) }
        viewModelScope.launch {
            when (val result = repository.getUpcomingLaunches(10, refresh)) {
                is com.sample.repositories.RepoResult.Success -> {
                    state.update {
                        it.copy(
                            isLoading = false,
                            results = result.result,
                            isError = false
                        )
                    }
                }
                is com.sample.repositories.RepoResult.Failure -> {
                    state.update { it.copy(isLoading = false, isError = true) }
                }
            }
        }
    }

    fun search(name: String) {
        viewModelScope.launch {
            when (val result = repository.getUpcomingLaunches(10, false)) {
                is com.sample.repositories.RepoResult.Success -> {
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
                is com.sample.repositories.RepoResult.Failure -> {
                    state.update { it.copy(isLoading = false, isError = true) }
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class UpcomingLaunchesViewModelFactory @Inject constructor(
    private val repository: LaunchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UpcomingLaunchesViewModel(repository = repository) as T
    }
}