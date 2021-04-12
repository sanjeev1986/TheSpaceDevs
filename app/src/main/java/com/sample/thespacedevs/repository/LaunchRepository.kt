package com.sample.thespacedevs.repository

import com.sample.thespacedevs.api.TheSpaceDevsRestApi
import com.sample.thespacedevs.api.launch.Results
import com.sample.thespacedevs.localstorage.InMemoryCache
import com.sample.thespacedevs.platform.errors.NotConnectedToInternet
import com.sample.thespacedevs.platform.hardware.ConnectivityApiManager
import com.sample.thespacedevs.utils.OpenForTesting
import com.sample.thespacedevs.utils.RepoResult
import com.sample.thespacedevs.utils.ui.AppDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class LaunchRepository @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val connectivityApiManager: ConnectivityApiManager,
    private val inMemoryCache: InMemoryCache,
    private val launchApi: TheSpaceDevsRestApi.LaunchApi
) {

    suspend fun getUpcomingLaunches(
        limit: Int,
        refresh: Boolean = true
    ): RepoResult<List<Results>> {
        return try {
            RepoResult.Success(
                if (refresh) {
                    fetchUpcomingLaunches(limit)
                } else {
                    inMemoryCache.getDataFromCache<List<Results>>(LaunchRepository::class.java.name)
                        ?: fetchUpcomingLaunches(limit)
                }
            )
        } catch (e: Exception) {
            RepoResult.Failure(e, null)
        }
    }

    private suspend fun fetchUpcomingLaunches(limit: Int): List<Results> {
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