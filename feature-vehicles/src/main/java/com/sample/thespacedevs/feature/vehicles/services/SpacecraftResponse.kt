package com.sample.thespacedevs.feature.vehicles.services

data class SpacecraftResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<SpaceCraft>
)