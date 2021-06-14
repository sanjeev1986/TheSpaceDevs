package com.sample.thespacedevs.feature.details

import android.content.Context
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun convertToBitmapDescriptor(context: Context, @DrawableRes drawable: Int): BitmapDescriptor {
    val background = androidx.core.content.ContextCompat.getDrawable(context, drawable)
    background!!.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
    val vectorDrawable = androidx.core.content.ContextCompat.getDrawable(context, drawable)
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = android.graphics.Bitmap.createBitmap(
        background.intrinsicWidth,
        background.intrinsicHeight,
        android.graphics.Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    background.draw(canvas)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}