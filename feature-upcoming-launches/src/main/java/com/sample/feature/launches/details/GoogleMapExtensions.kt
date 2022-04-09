package com.sample.feature.launches.details

import android.content.Context
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun convertToBitmapDescriptor(context: Context, @DrawableRes drawable: Int): BitmapDescriptor {
    val background = ContextCompat.getDrawable(context, drawable)
    background!!.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
    val bitmap = android.graphics.Bitmap.createBitmap(
        background.intrinsicWidth,
        background.intrinsicHeight,
        android.graphics.Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    background.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}