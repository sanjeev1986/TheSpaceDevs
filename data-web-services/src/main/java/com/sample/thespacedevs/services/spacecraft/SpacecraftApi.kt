package com.sample.thespacedevs.services.spacecraft

import retrofit2.http.GET
import retrofit2.http.Query

interface SpacecraftApi {
    @GET("spacecraft/")
    suspend fun fetchSpacecrafts(@Query("limit") limit: Int): SpacecraftResponse
}