package com.sample.ds.compose

import android.graphics.Typeface
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.sample.ds.platformBlack

val Typography.Title: TextStyle
    get() = TextStyle(
        color = platformBlack,
        fontFamily = FontFamily(
            typeface = Typeface.create(
                "sans-serif-condensed-medium",
                Typeface.NORMAL
            )
        ),
        fontSize = 18.sp
    )


val Typography.ListItemTitle: TextStyle
    get() = TextStyle(
        color = platformBlack,
        fontFamily = FontFamily(
            typeface = Typeface.create(
                "sans-serif-condensed-medium",
                Typeface.NORMAL
            )
        ),
        fontSize = 16.sp
    )

val Typography.ListItemSubTitle: TextStyle
    get() = TextStyle(
        color = platformBlack,
        fontFamily = FontFamily(
            typeface = Typeface.create(
                "sans-serif-condensed-light",
                Typeface.NORMAL
            )
        ),
        fontSize = 14.sp
    )

val Typography.ListItemBody: TextStyle
    get() = TextStyle(
        color = platformBlack,
        fontFamily = FontFamily(
            typeface = Typeface.create(
                "sans-serif-condensed-light",
                Typeface.NORMAL
            )
        ),
        fontSize = 12.sp
    )