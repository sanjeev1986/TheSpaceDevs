package com.sample.thespacedevs.feature.vehicles

import com.sample.platform.hardware.ConnectivityApiManager
import com.sample.thespacedevs.services.spacecraft.SpacecraftApi

import com.sample.thespacedevs.services.spacecraft.SpacecraftResponse
import com.sample.platform.storage.InMemoryCache
import com.sample.base.RepoResult
import javax.inject.Inject

class SpacecraftRepository @Inject constructor(
    private val connectivityApiManager: ConnectivityApiManager,
    private val inMemoryCache: InMemoryCache,
    private val spacecraftApi: SpacecraftApi
) {
    companion object {
        private val LIMIT = 20
    }

    suspend fun getSpacecrafts(refresh: Boolean = false): com.sample.base.RepoResult<SpacecraftResponse> {
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