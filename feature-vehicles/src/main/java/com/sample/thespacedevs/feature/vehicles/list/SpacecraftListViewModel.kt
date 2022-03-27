package com.sample.thespacedevs.feature.vehicles.list

import androidx.lifecycle.*
import com.sample.thespacedevs.feature.vehicles.SpacecraftRepository
import com.sample.thespacedevs.utils.RepoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class SpacecraftListViewModel @Inject constructor(private val repository: SpacecraftRepository) :
    ViewModel() {

    private val state = MutableStateFlow(SpaceCraftsViewState())
    val spaceCrafts = state.asLiveData().map { it.spaceCrafts }
    val loading = state.asLiveData().map { it.isError }
    val error = state.asLiveData().map { it.isError }

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