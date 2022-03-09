package com.sample.ds.compose

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

data class DSShape(
    val roundedCornerWidget: RoundedCornerShape = RoundedCornerShape(4.dp),
)

internal val LocalDSShapeConfiguration = staticCompositionLocalOf { DSShape() }