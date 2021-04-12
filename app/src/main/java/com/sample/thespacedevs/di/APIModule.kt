package com.sample.thespacedevs.di

import com.google.gson.Gson
import com.sample.thespacedevs.BuildConfig
import com.sample.thespacedevs.api.TheSpaceDevsRestApi
import com.sample.thespacedevs.utils.network.HttpStack
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class APIModule {

    @Provides
    @Singleton
    fun provideHttpStack(gson: Gson): HttpStack {
        return HttpStack(BuildConfig.BASE_URL, gson)
    }

    @Provides
    @Singleton
    fun provideLaunchApi(httpStack: HttpStack): TheSpaceDevsRestApi.LaunchApi {
        return httpStack.retrofit.create(TheSpaceDevsRestApi.LaunchApi::class.java)
    }
}