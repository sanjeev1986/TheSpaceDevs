package com.sample.thespacedevs.di

import com.sample.thespacedevs.utils.AppDispatchers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DispatchersModule {
    @Singleton
    @Provides
    fun provideDispatchers() = AppDispatchers()
}