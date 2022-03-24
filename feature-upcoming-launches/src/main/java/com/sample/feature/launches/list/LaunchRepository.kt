package com.sample.feature.launches.list

import com.sample.thespacedevs.services.TheSpaceDevsService
import com.sample.thespacedevs.services.launch.Results
import com.sample.platform.errors.NotConnectedToInternet
import com.sample.platform.hardware.ConnectivityApiManager
import com.sample.thespacedevs.utils.InMemoryCache
import com.sample.thespacedevs.utils.RepoResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

//@OpenForTesting
@Singleton
class LaunchRepository @Inject constructor(
    private val connectivityApiManager: ConnectivityApiManager,
    private val inMemoryCache: InMemoryCache,
    private val launchApi: TheSpaceDevsService.LaunchApi
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
                        LaunchRepository::class.java.name
                    )
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

    fun getLaunchDetailsById(id: String): Results? {
        return inMemoryCache.getDataFromCache<List<Results>>( LaunchRepository::class.java.name)?.find { it.id == id }
    }
}