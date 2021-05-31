package com.sample.thespacedevs.repository

import com.sample.repositories.localstorage.InMemoryCache
import com.sample.platform.hardware.ConnectivityApiManager
import com.sample.thespacedevs.utils.ui.AppDispatchers
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LaunchRepositoryTest {
    private var dispatchers: AppDispatchers = AppDispatchers(Dispatchers.IO, Dispatchers.IO)
    private var connectivityApiManager: com.sample.platform.hardware.ConnectivityApiManager = mockk(relaxed = true)
    private var inMemoryCache: com.sample.repositories.localstorage.InMemoryCache = mockk(relaxed = true)
    private var launchApi: com.sample.services.TheSpaceDevsRestApi.LaunchApi = mockk(relaxed = true)
    private val successResponse = com.sample.services.launch.UpcomingLaunchesResponse(
        results = listOf(
            com.sample.services.launch.Results(
                id = "1",
                name = "New Shepard | NS-15",
                window_start = "2021-04-14T12:00:00Z",
                mission = com.sample.services.launch.Mission(
                    name = "NS-15",
                    description = "Fifteenth flight of Blue Origin's New Shepard rocket"
                ),
                pad = com.sample.services.launch.Pad(
                    name = "West Texas Suborbital Launch Site/ Corn Ranch",
                    latitude = 31.422878000000000,
                    longitude = -104.757121000000000
                )
            ),
            com.sample.services.launch.Results(
                id = "2",
                name = "Falcon 9 Block 5 | SpX USCV-2 (NASA Crew Flight 2)",
                window_start = "2021-04-22T10:11:00Z",
                mission = com.sample.services.launch.Mission(
                    name = "SpX USCV-2 (NASA Crew Flight 2)",
                    description = "SpaceX Crew-2 will be the second crewed operational flight of a Crew Dragon spacecraft, and the third overall crewed orbital flight. It will use the same Falcon 9 first stage as the Crew-1 mission and the same Crew Dragon capsule as the Demo-2 mission (Endeavour)."
                ),
                pad = com.sample.services.launch.Pad(
                    name = "Launch Complex 39A",
                    latitude = 28.60822681,
                    longitude = -80.60428186
                )
            ),
            com.sample.services.launch.Results(
                id = "3",
                name = "Soyuz 2.1b/Fregat-M | OneWeb 6",
                window_start = "2021-04-25T00:00:00Z",
                mission = com.sample.services.launch.Mission(
                    name = "OneWeb 6",
                    description = "A batch of 36 satellites for the OneWeb satellite constellation, which is intended to provide global Interned broadband service for individual consumers. The constellation is planned to have around 648 microsatellites (of which 60 are spares), around 150 kg each, operating in Ku-band from low Earth orbit."
                ),
                pad = com.sample.services.launch.Pad(
                    name = "Cosmodrome Site 1S",
                    latitude = 51.884395,
                    longitude = 128.333932
                )
            )
        )
    )

    @Test
    fun testUpcomngLaunchesTest() {
        every { connectivityApiManager.isConnectedToInternet() } returns true
        coEvery { launchApi.fetchUpcomingLaunches(eq(10)) } returns successResponse
        every { inMemoryCache.getDataFromCache<List<com.sample.services.launch.Results>>(any()) } returns null
        runBlocking {
            val repository = com.sample.repositories.LaunchRepository(
                dispatchers,
                connectivityApiManager,
                inMemoryCache,
                launchApi
            )
            repository.getUpcomingLaunches(10, true)
            coVerify(exactly = 1) { launchApi.fetchUpcomingLaunches(eq(10)) }
        }
    }


    @Test
    fun testUpcomngLaunchesCacheHit() {
        every { connectivityApiManager.isConnectedToInternet() } returns true
        coEvery { launchApi.fetchUpcomingLaunches(eq(10)) } returns successResponse
        every { inMemoryCache.getDataFromCache<List<com.sample.services.launch.Results>>(any()) } returns successResponse.results
        runBlocking {
            val repository = com.sample.repositories.LaunchRepository(
                dispatchers,
                connectivityApiManager,
                inMemoryCache,
                launchApi
            )
            repository.getUpcomingLaunches(10, false)
            coVerify(exactly = 0) { launchApi.fetchUpcomingLaunches(eq(10)) }
        }
    }

    @Test
    fun testUpcomngLaunchesCallsApiWhenCacheMiss() {
        every { connectivityApiManager.isConnectedToInternet() } returns true
        coEvery { launchApi.fetchUpcomingLaunches(eq(10)) } returns successResponse
        every { inMemoryCache.getDataFromCache<List<com.sample.services.launch.Results>>(any()) } returns null
        runBlocking {
            val repository = com.sample.repositories.LaunchRepository(
                dispatchers,
                connectivityApiManager,
                inMemoryCache,
                launchApi
            )
            repository.getUpcomingLaunches(10, false)
            coVerify(exactly = 1) { launchApi.fetchUpcomingLaunches(eq(10)) }
        }
    }
}