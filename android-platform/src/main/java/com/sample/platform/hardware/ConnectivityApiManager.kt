package com.sample.platform.hardware

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityApiManager @Inject constructor(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isConnectedToInternet(): Boolean =
        connectivityManager.activeNetworkInfo?.isConnected ?: false
}
