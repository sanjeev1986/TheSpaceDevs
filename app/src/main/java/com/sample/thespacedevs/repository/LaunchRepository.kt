package com.sample.thespacedevs.repository

import com.sample.services.TheSpaceDevsRestApi
import com.sample.services.launch.Results
import com.sample.thespacedevs.localstorage.InMemoryCache
import com.sample.thespacedevs.platform.errors.NotConnectedToInternet
import com.sample.thespacedevs.platform.hardware.ConnectivityApiManager
import com.sample.thespacedevs.utils.OpenForTesting
import com.sample.thespacedevs.utils.RepoResult
import com.sample.thespacedevs.utils.ui.AppDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class LaunchRepository @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val connectivityApiManager: ConnectivityApiManager,
    private val inMemoryCache: InMemoryCache,
    private val launchApi: com.sample.services.TheSpaceDevsRestApi.LaunchApi
) {

    suspend fun getUpcomingLaunches(
        limit: Int,
        refresh: Boolean = true
    ): RepoResult<List<com.sample.services.launch.Results>> {
        return try {
            RepoResult.Success(
                if (refresh) {
                    fetchUpcomingLaunches(limit)
                } else {
                    inMemoryCache.getDataFromCache<List<com.sample.services.launch.Results>>(LaunchRepository::class.java.name)
                        ?: fetchUpcomingLaunches(limit)
                }
            )
        } catch (e: Exception) {
            RepoResult.Failure(e, null)
        }
    }

    private suspend fun fetchUpcomingLaunches(limit: Int): List<com.sample.services.launch.Results> {
        if (!connectivityApiManager.isConnectedToInternet()) {
            throw NotConnectedToInternet()
        }
        return withContext(dispatchers.io) {
            inMemoryCache.removeFromCache(LaunchRepository::class.java.name)
            launchApi.fetchUpcomingLaunches(limit).let { it.results }.also {
                inMemoryCache.saveToInMemoryCache(LaunchRepository::class.java.name, it)
            }

        }
    }
}