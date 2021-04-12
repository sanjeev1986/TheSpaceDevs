package com.sample.thespacedevs.di

import com.sample.thespacedevs.utils.ui.AppDispatchers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DispatchersModule {
    @Singleton
    @Provides
    fun provideDispatchers() = AppDispatchers()
}