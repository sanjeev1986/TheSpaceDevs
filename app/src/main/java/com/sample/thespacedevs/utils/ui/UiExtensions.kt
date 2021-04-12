package com.sample.thespacedevs.utils.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

/**
 * Convert a vector drawable to Bitmap
 */
fun GoogleMap.convertToBitmapDescriptor(context: Context, @DrawableRes drawable: Int): BitmapDescriptor {
    val background = ContextCompat.getDrawable(context, drawable)
    background!!.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
    val vectorDrawable = ContextCompat.getDrawable(context, drawable)
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(
        background.intrinsicWidth,
        background.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    background.draw(canvas)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}