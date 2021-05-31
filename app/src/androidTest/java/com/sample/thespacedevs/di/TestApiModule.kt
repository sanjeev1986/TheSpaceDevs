package com.sample.thespacedevs.di

import com.sample.services.TheSpaceDevsRestApi
import com.sample.services.HttpStack
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
    fun provideLaunchApi() = mockk<com.sample.services.TheSpaceDevsRestApi.LaunchApi>()
}