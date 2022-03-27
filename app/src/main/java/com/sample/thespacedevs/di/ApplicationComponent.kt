package com.sample.thespacedevs.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        APIModule::class,
        PlatformModule::class,
        StorageModule::class,
        DispatchersModule::class
    ]
)
interface ApplicationModulesAggregator {}
