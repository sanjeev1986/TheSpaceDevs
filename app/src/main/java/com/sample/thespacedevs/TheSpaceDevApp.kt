package com.sample.thespacedevs

import androidx.fragment.app.Fragment
import com.sample.thespacedevs.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class TheSpaceDevApp : DaggerApplication() {
    companion object {

        fun getInstance(fragment: Fragment): TheSpaceDevApp =
            fragment.requireActivity().applicationContext as TheSpaceDevApp
    }


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

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(this)
    }

}