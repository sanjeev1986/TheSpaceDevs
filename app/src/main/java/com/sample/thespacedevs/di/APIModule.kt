package com.sample.thespacedevs.di

import android.content.Context
import com.google.gson.Gson
import com.sample.thespacedevs.services.HttpStack
import com.sample.services.BuildConfig
import com.sample.thespacedevs.services.LaunchApi
import com.sample.thespacedevs.services.SpacecraftApi
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
        return HttpStack(BuildConfig.BASE_URL, Gson(), context)
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