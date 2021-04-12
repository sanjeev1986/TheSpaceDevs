package com.sample.thespacedevs.di

import android.content.Context
import com.sample.thespacedevs.localstorage.InMemoryCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Provides
    @Singleton
    fun provideInMemoryCache(context: Context): InMemoryCache {
        return InMemoryCache()
    }
}