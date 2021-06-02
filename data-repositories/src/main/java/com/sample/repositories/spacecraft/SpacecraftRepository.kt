package com.sample.repositories.spacecraft

import com.sample.platform.hardware.ConnectivityApiManager
import com.sample.repositories.RepoResult
import com.sample.repositories.localstorage.InMemoryCache
import com.sample.thespacedevs.services.TheSpaceDevsService
import com.sample.thespacedevs.services.spacecraft.Results
import javax.inject.Inject

class SpacecraftRepository @Inject constructor(
    private val connectivityApiManager: ConnectivityApiManager,
    private val inMemoryCache: InMemoryCache,
    private val spacecraftApi: TheSpaceDevsService.SpacecraftApi
) {
    companion object {
        private val LIMIT = 20
    }

    suspend fun getSpacecrafts(refresh: Boolean = false): RepoResult<List<Results>> {
        return try {
            RepoResult.Success(
                if (refresh) {
                    spacecraftApi.fetchSpacecrafts(LIMIT).results
                } else {
                    inMemoryCache.getDataFromCache<List<Results>>(
                        SpacecraftRepository::class.java.name
                    )
                        ?: spacecraftApi.fetchSpacecrafts(LIMIT).results
                }
            )
        } catch (e: Exception) {
            RepoResult.Failure(e, emptyList())
        }
    }
}