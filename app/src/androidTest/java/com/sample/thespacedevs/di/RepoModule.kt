package com.sample.thespacedevs.di

import com.google.gson.Gson
import com.sample.thespacedevs.repository.LaunchRepository
import com.sample.thespacedevs.utils.network.HttpStack
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class RepoModule {
    @Provides
    @Singleton
    fun provideRepository(): LaunchRepository{
        return mockk<LaunchRepository>()
    }

}