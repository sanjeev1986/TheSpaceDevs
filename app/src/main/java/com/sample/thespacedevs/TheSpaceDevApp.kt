package com.sample.thespacedevs

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class TheSpaceDevApp : Application() {

    override fun onCreate() {
        super.onCreate()
        /*if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }*/
    }
}