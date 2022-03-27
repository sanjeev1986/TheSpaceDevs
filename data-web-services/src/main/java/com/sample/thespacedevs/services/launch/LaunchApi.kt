package com.sample.thespacedevs.services.launch

import retrofit2.http.GET
import retrofit2.http.Query

interface LaunchApi {
    @GET("launch/upcoming")
    suspend fun fetchUpcomingLaunches(@Query("limit") limit: Int): UpcomingLaunchesResponse
}
