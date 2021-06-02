package com.sample.thespacedevs.services

import com.sample.thespacedevs.services.launch.UpcomingLaunchesResponse
import com.sample.thespacedevs.services.spacecraft.SpacecraftResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheSpaceDevsService {

    interface LaunchApi {
        @GET("launch/upcoming")
        suspend fun fetchUpcomingLaunches(@Query("limit") limit: Int): UpcomingLaunchesResponse
    }

    interface SpacecraftApi {
        @GET("spacecraft/")
        suspend fun fetchSpacecrafts(@Query("limit") limit: Int): SpacecraftResponse
    }
}