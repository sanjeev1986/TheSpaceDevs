package com.sample.services

import com.sample.services.launch.UpcomingLaunchesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheSpaceDevsRestApi {

    interface LaunchApi {
        @GET("launch/upcoming")
        suspend fun fetchUpcomingLaunches(@Query("limit") limit: Int): UpcomingLaunchesResponse
    }
}