package com.sample.thespacedevs.feature.vehicles.list

import androidx.lifecycle.*
import com.sample.repositories.RepoResult
import com.sample.repositories.spacecraft.SpacecraftRepository
import com.sample.thespacedevs.services.spacecraft.SpaceCraft
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SpacecraftListViewModel(private val repository: SpacecraftRepository) : ViewModel() {

    private val state = MutableStateFlow(SpaceCraftsViewState())
    val spaceCrafts = state.asLiveData().map { it.spaceCrafts }
    val loading = state.asLiveData().map { it.isError }
    val error = state.asLiveData().map { it.isError }
    private val _spacecraftsLiveData = MutableLiveData<RepoResult<List<SpaceCraft>>>()

    init {
        fetchSpacecrafts()
    }

    fun fetchSpacecrafts(isRefresh: Boolean = false) {
        state.value = state.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.getSpacecrafts(isRefresh)
            }
            when (response) {
                is RepoResult.Success -> {
                    state.value =
                        state.value.copy(
                            isLoading = false,
                            isError = false,
                            spaceCrafts = response.result.results.map {
                                SpaceCraftViewData(
                                    imageUrl = it.spacecraft_config.image_url,
                                    name = it.name,
                                    status = it.status.name,
                                    description = it.description
                                )
                            })
                }
                is RepoResult.Failure -> {
                    state.value = state.value.copy(isLoading = false, isError = true)
                }
            }
        }
    }
}

open class SpacecraftListViewModelFactory @Inject constructor(private val repository: SpacecraftRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SpacecraftListViewModel(repository) as T
    }
}