package com.sample.thespacedevs.di

import android.content.Context
import com.google.gson.Gson
import com.sample.thespacedevs.services.HttpStack
import com.sample.services.BuildConfig
import com.sample.thespacedevs.services.TheSpaceDevsService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class APIModule {

    @Provides
    @Singleton
    fun provideHttpStack(context: Context): HttpStack {
        return HttpStack(BuildConfig.BASE_URL, Gson(), context)
    }

    @Provides
    @Singleton
    fun provideLaunchApi(httpStack: HttpStack): TheSpaceDevsService.LaunchApi {
        return httpStack.create(TheSpaceDevsService.LaunchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSpacecraftApi(httpStack: HttpStack): TheSpaceDevsService.SpacecraftApi {
        return httpStack.create(TheSpaceDevsService.SpacecraftApi::class.java)
    }
}