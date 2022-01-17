package com.sample.ds.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class DSColors(
    primary: Color,
    secondary: Color,
    accent: Color,
    textPrimary: Color,
    error: Color,
    windowBackground: Color
) {
    var primary: Color by mutableStateOf(primary)
    var secondary: Color by mutableStateOf(secondary)
    var accent: Color by mutableStateOf(accent)
    var textPrimary: Color by mutableStateOf(textPrimary)
    var error: Color by mutableStateOf(error)
    var windowBackground: Color by mutableStateOf(windowBackground)
}


val colorPrimary = Color(0xff262626)
val colorPrimaryDark = Color(0xff262626)
val colorAccent = Color(0xff262626)
val windowBackground = Color(0xff262626)

val platformWhite = Color.White
val platformBlack = Color.Black
val dividerGrey = Color(0xffaaaaaa)

internal val LocalDSColorConfiguration = staticCompositionLocalOf {
    DSColors(
        primary = colorPrimary,
        secondary = colorPrimaryDark,
        accent = colorAccent,
        textPrimary = platformBlack,
        windowBackground = platformWhite,
        error = colorPrimary
    )
}