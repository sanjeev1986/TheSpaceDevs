package com.sample.repositories

import com.sample.repositories.localstorage.InMemoryCache
import com.sample.services.TheSpaceDevsRestApi
import com.sample.services.launch.Results
import com.sample.platform.errors.NotConnectedToInternet
import com.sample.platform.hardware.ConnectivityApiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

//@OpenForTesting
@Singleton
class LaunchRepository @Inject constructor(
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
                    inMemoryCache.getDataFromCache<List<Results>>(
                        LaunchRepository::class.java.name)
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
        return withContext(Dispatchers.IO) {
            inMemoryCache.removeFromCache(LaunchRepository::class.java.name)
            launchApi.fetchUpcomingLaunches(limit).let { it.results }.also {
                inMemoryCache.saveToInMemoryCache(LaunchRepository::class.java.name, it)
            }

        }
    }
}