package com.sample.ds.compose

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DSElevation(
    val flat: Dp = 0.dp,
    val raised: Dp = 2.dp,
    val overlay: Dp = 8.dp,
)

internal val LocalDSElevationConfiguration = staticCompositionLocalOf { DSElevation() }