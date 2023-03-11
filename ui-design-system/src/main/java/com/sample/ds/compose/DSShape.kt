package com.sample.ds.compose

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

data class DSShape(
    val roundedCornerWidget: RoundedCornerShape = RoundedCornerShape(4.dp),
    val circle: RoundedCornerShape = CircleShape,
)

internal val LocalDSShapeConfiguration = staticCompositionLocalOf { DSShape() }
