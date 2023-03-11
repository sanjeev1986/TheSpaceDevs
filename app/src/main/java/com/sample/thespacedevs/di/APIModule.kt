package com.sample.thespacedevs.di

import android.content.Context
import com.google.gson.Gson
import com.sample.base.HttpStack
import com.sample.feature.launches.services.LaunchApi
import com.sample.thespacedevs.feature.vehicles.services.SpacecraftApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class HttpStackMarker

    @Singleton
    @HttpStackMarker
    @Provides
    fun provideHttpStack(@ApplicationContext context: Context): HttpStack {
        return HttpStack("https://ll.thespacedevs.com/2.0.0/", Gson(), context.cacheDir)
    }

    @Singleton
    @Provides
    fun provideLaunchApi(@HttpStackMarker httpStack: HttpStack): LaunchApi {
        return httpStack.create(LaunchApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSpacecraftApi(@HttpStackMarker httpStack: HttpStack): SpacecraftApi {
        return httpStack.create(SpacecraftApi::class.java)
    }
}
