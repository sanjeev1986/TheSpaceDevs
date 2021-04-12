package com.sample.thespacedevs.di

import com.google.gson.Gson
import com.sample.thespacedevs.api.TheSpaceDevsRestApi
import com.sample.thespacedevs.utils.network.HttpStack
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
    fun provideLaunchApi() = mockk<TheSpaceDevsRestApi.LaunchApi>()
}