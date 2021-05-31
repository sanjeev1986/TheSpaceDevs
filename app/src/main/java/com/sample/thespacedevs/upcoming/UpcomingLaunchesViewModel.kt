package com.sample.thespacedevs.upcoming

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sample.repositories.LaunchRepository
import com.sample.repositories.RepoResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpcomingLaunchesViewModel(
    private val repository: com.sample.repositories.LaunchRepository
) : ViewModel() {
    private val _upcomingLaunchesLiveData = MutableLiveData<Result<List<com.sample.services.launch.Results>>>()
    val upcomingLaunchesLiveData = _upcomingLaunchesLiveData

    fun fetchUpcomingLaunches(refresh: Boolean) {
        viewModelScope.launch {
            when (val result = repository.getUpcomingLaunches(10, refresh)) {
                is com.sample.repositories.RepoResult.Success -> {
                    _upcomingLaunchesLiveData.value = Result.success(result.result)
                }
                is com.sample.repositories.RepoResult.Failure -> {
                    _upcomingLaunchesLiveData.value = Result.failure(result.error)
                }
            }
        }
    }

    fun search(name: String) {
        viewModelScope.launch {
            when (val result = repository.getUpcomingLaunches(10, false)) {
                is com.sample.repositories.RepoResult.Success -> {
                    _upcomingLaunchesLiveData.value =
                        Result.success(result.result.filter { it.name.startsWith(name, ignoreCase = true) })
                }
                is com.sample.repositories.RepoResult.Failure -> {
                    _upcomingLaunchesLiveData.value = Result.failure(result.error)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class VMFactory @Inject constructor(
        private val repository: com.sample.repositories.LaunchRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UpcomingLaunchesViewModel(repository) as T
        }
    }
}