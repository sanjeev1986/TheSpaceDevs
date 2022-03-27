package com.sample.thespacedevs.di

import android.content.Context
import com.sample.thespacedevs.utils.InMemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    @Singleton
    fun provideInMemoryCache(@ApplicationContext context: Context): InMemoryCache {
        return InMemoryCache()
    }
}