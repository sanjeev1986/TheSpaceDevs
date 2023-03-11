package com.sample.ds.compose

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DSSizes(
    val screenMargin: Dp = 16.dp,
    val screenHalfMargin: Dp = 8.dp,
    val listItemPadding: Dp = 8.dp,
    val formWidgetMargin: Dp = 16.dp,
    val formWidgetHalfMargin: Dp = 8.dp,
    val widgetPadding: Dp = 8.dp,
)

internal val LocalDSSizeConfiguration = staticCompositionLocalOf { DSSizes() }
