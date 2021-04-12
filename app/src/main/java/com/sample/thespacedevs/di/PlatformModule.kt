package com.sample.thespacedevs.di

import android.content.Context
import com.sample.thespacedevs.platform.hardware.ConnectivityApiManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PlatformModule {
    @Provides
    @Singleton
    fun provideConnectivityApiManager(context: Context): ConnectivityApiManager {
        return ConnectivityApiManager(context)
    }


}