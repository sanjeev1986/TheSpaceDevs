package com.sample.thespacedevs.di

import com.google.gson.Gson
import com.sample.services.HttpStack
import com.sample.services.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class APIModule {

    @Provides
    @Singleton
    fun provideHttpStack(): HttpStack {
        return HttpStack(BuildConfig.BASE_URL, Gson())
    }

    @Provides
    @Singleton
    fun provideLaunchApi(httpStack: HttpStack): com.sample.services.TheSpaceDevsRestApi.LaunchApi {
        return httpStack.create(com.sample.services.TheSpaceDevsRestApi.LaunchApi::class.java)
    }
}