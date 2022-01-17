package com.sample.ds.compose

import android.graphics.Typeface
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

data class DSTypography(

    val Title: TextStyle = TextStyle(
        color = platformBlack,
        fontFamily = FontFamily(
            typeface = Typeface.create(
                "sans-serif-condensed-medium",
                Typeface.NORMAL
            )
        ),
        fontSize = 18.sp
    ),

    val ListItemTitle: TextStyle = TextStyle(
        color = platformBlack,
        fontFamily = FontFamily(
            typeface = Typeface.create(
                "sans-serif-condensed-medium",
                Typeface.NORMAL
            )
        ),
        fontSize = 16.sp
    ),

    val ListItemSubTitle: TextStyle = TextStyle(
        color = platformBlack,
        fontFamily = FontFamily(
            typeface = Typeface.create(
                "sans-serif-condensed-light",
                Typeface.NORMAL
            )
        ),
        fontSize = 14.sp
    ),

    val ListItemBody: TextStyle = TextStyle(
        color = platformBlack,
        fontFamily = FontFamily(
            typeface = Typeface.create(
                "sans-serif-condensed-light",
                Typeface.NORMAL
            )
        ),
        fontSize = 12.sp
    )
)

internal val LocalDSTypographyConfiguration = staticCompositionLocalOf { DSTypography() }