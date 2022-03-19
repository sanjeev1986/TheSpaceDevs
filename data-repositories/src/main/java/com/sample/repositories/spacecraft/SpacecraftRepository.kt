package com.sample.repositories.spacecraft

import com.sample.platform.hardware.ConnectivityApiManager
import com.sample.repositories.RepoResult
import com.sample.repositories.localstorage.InMemoryCache
import com.sample.thespacedevs.services.TheSpaceDevsService
import com.sample.thespacedevs.services.spacecraft.SpaceCraft
import com.sample.thespacedevs.services.spacecraft.SpacecraftResponse
import javax.inject.Inject

class SpacecraftRepository @Inject constructor(
    private val connectivityApiManager: ConnectivityApiManager,
    private val inMemoryCache: InMemoryCache,
    private val spacecraftApi: TheSpaceDevsService.SpacecraftApi
) {
    companion object {
        private val LIMIT = 20
    }

    suspend fun getSpacecrafts(refresh: Boolean = false): RepoResult<SpacecraftResponse> {
        return try {
            RepoResult.Success(
                if (refresh) {
                    spacecraftApi.fetchSpacecrafts(LIMIT)
                } else {
                    inMemoryCache.getDataFromCache<SpacecraftResponse>(
                        SpacecraftRepository::class.java.name
                    )
                        ?: spacecraftApi.fetchSpacecrafts(LIMIT)
                }
            )
        } catch (e: Exception) {
            RepoResult.Failure(e)
        }
    }
}