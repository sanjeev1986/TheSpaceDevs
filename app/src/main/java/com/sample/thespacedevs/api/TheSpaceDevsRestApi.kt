package com.sample.thespacedevs.api

import com.sample.thespacedevs.api.launch.UpcomingLaunchesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheSpaceDevsRestApi {

    interface LaunchApi {
        @GET("launch/upcoming")
        suspend fun fetchUpcomingLaunches(@Query("limit") limit: Int): UpcomingLaunchesResponse
    }
}