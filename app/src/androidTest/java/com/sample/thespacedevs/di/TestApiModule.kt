package com.sample.thespacedevs.di

import com.sample.base.HttpStack
import com.sample.thespacedevs.services.TheSpaceDevsService
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class TestApiModule {
    @Provides
    @Singleton
    fun provideHttpStack() = mockk<HttpStack>()

    @Provides
    @Singleton
    fun provideLaunchApi() = mockk<TheSpaceDevsService.LaunchApi>()
}
