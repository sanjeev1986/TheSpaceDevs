package com.sample.thespacedevs.di

import com.sample.thespacedevs.utils.ui.AppDispatchers
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class DispatchersModuleTest {
    @Singleton
    @Provides
    fun provideDispatchers() = AppDispatchers(Dispatchers.Main, Dispatchers.Main)
}