package com.sample.thespacedevs.di

import android.content.Context
import com.sample.platform.hardware.ConnectivityApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlatformModule {
    @Provides
    @Singleton
    fun provideConnectivityApiManager(@ApplicationContext context: Context): ConnectivityApiManager {
        return ConnectivityApiManager(context)
    }
}