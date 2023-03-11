package com.sample.thespacedevs.feature.vehicles.services

import retrofit2.http.GET
import retrofit2.http.Query

interface SpacecraftApi {
    @GET("spacecraft/")
    suspend fun fetchSpacecrafts(@Query("limit") limit: Int): SpacecraftResponse
}