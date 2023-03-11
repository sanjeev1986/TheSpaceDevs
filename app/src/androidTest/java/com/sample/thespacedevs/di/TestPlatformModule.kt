package com.sample.thespacedevs.di

import com.sample.platform.hardware.ConnectivityApiManager
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class TestPlatformModule {
    @Provides
    @Singleton
    fun provideConnectivityApiManager(): com.sample.platform.hardware.ConnectivityApiManager = mockk(relaxed = true)
}
