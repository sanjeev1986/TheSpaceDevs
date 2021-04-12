package com.sample.thespacedevs.di

import android.content.Context
import com.sample.thespacedevs.localstorage.InMemoryCache
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class StorageModuleTest {
    @Provides
    @Singleton
    fun provideInMemoryCache() = mockk<InMemoryCache>()
}