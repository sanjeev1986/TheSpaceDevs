package com.sample.thespacedevs

import android.app.Application
import android.os.StrictMode
import androidx.fragment.app.Fragment
import com.sample.thespacedevs.di.DaggerApplicationComponent
import com.sample.thespacedevs.di.DaggerComponent

open class TheSpaceDevApp : Application() {
    companion object {

        fun getInstance(fragment: Fragment): TheSpaceDevApp =
            fragment.requireActivity().applicationContext as TheSpaceDevApp
    }

    val applicationComponent: DaggerComponent by lazy {
        createDaggerComponent()
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

    open fun createDaggerComponent(): DaggerComponent {
        return DaggerApplicationComponent.builder().application(this).context(this).build()
    }
}