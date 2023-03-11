package com.sample.feature.launches.services

import retrofit2.http.GET
import retrofit2.http.Query

interface LaunchApi {
    @GET("launch/upcoming")
    suspend fun fetchUpcomingLaunches(@Query("limit") limit: Int): UpcomingLaunchesResponse
}