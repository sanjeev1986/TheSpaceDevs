package com.sample.platform.hardware

import android.content.Context
import android.net.ConnectivityManager
//import com.sample.thespacedevs.utils.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

//@OpenForTesting
@Singleton
class ConnectivityApiManager @Inject constructor(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isConnectedToInternet(): Boolean =
        connectivityManager.activeNetworkInfo?.isConnected ?: false

}