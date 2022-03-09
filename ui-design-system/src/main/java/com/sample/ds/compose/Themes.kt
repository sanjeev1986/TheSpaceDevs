package com.sample.ds.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object DSTheme {
    val colors: DSColors
        @Composable
        @ReadOnlyComposable
        get() = LocalDSColorConfiguration.current

    val typography: DSTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalDSTypographyConfiguration.current

    val sizes: DSSizes
        @Composable
        @ReadOnlyComposable
        get() = LocalDSSizeConfiguration.current

    val elevation: DSElevation
        @Composable
        @ReadOnlyComposable
        get() = LocalDSElevationConfiguration.current

    val shape: DSShape
        @Composable
        @ReadOnlyComposable
        get() = LocalDSShapeConfiguration.current
}

@Composable
fun AppTheme(
    colors: DSColors = DSTheme.colors,
    typography: DSTypography = DSTheme.typography,
    sizes: DSSizes = DSTheme.sizes,
    contentSlot: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalDSColorConfiguration provides colors,
        LocalDSTypographyConfiguration provides typography,
        LocalDSSizeConfiguration provides sizes
    ) {
        contentSlot()
    }
}