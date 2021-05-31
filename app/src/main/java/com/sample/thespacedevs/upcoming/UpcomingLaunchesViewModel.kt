package com.sample.thespacedevs.upcoming

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sample.services.launch.Results
import com.sample.thespacedevs.repository.LaunchRepository
import com.sample.thespacedevs.utils.RepoResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpcomingLaunchesViewModel(
    private val repository: LaunchRepository
) : ViewModel() {
    private val _upcomingLaunchesLiveData = MutableLiveData<Result<List<com.sample.services.launch.Results>>>()
    val upcomingLaunchesLiveData = _upcomingLaunchesLiveData

    fun fetchUpcomingLaunches(refresh: Boolean) {
        viewModelScope.launch {
            when (val result = repository.getUpcomingLaunches(10, refresh)) {
                is RepoResult.Success -> {
                    _upcomingLaunchesLiveData.value = Result.success(result.result)
                }
                is RepoResult.Failure -> {
                    _upcomingLaunchesLiveData.value = Result.failure(result.error)
                }
            }
        }
    }

    fun search(name: String) {
        viewModelScope.launch {
            when (val result = repository.getUpcomingLaunches(10, false)) {
                is RepoResult.Success -> {
                    _upcomingLaunchesLiveData.value =
                        Result.success(result.result.filter { it.name.startsWith(name, ignoreCase = true) })
                }
                is RepoResult.Failure -> {
                    _upcomingLaunchesLiveData.value = Result.failure(result.error)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class VMFactory @Inject constructor(
        private val repository: LaunchRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UpcomingLaunchesViewModel(repository) as T
        }
    }
}