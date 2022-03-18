package com.sample.thespacedevs.feature.spacecrafts

import androidx.lifecycle.*
import com.sample.repositories.RepoResult
import com.sample.repositories.spacecraft.SpacecraftRepository
import com.sample.thespacedevs.services.spacecraft.SpaceCraft
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SpacecraftListViewModel(private val repository: SpacecraftRepository) : ViewModel() {

    private val state = MutableStateFlow<List<SpaceCraft>>(emptyList())
    val spaceCrafts = state.asLiveData()
    val loading = state.asLiveData().map { }
    private val _spacecraftsLiveData = MutableLiveData<RepoResult<List<SpaceCraft>>>()
    val spacecraftsLiveData: LiveData<RepoResult<List<SpaceCraft>>>
        get() = _spacecraftsLiveData

    class Factory @Inject constructor(private val repository: SpacecraftRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SpacecraftListViewModel(repository) as T
        }
    }

    fun fetchSpacecrafts(isRefresh: Boolean = false) {
        viewModelScope.launch {
            val results = withContext(Dispatchers.IO) {
                repository.getSpacecrafts(isRefresh)
            }
            _spacecraftsLiveData.value = results
        }
    }
}