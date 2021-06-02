package com.sample.thespacedevs.di

import com.sample.repositories.launch.LaunchRepository
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class RepoModule {
    @Provides
    @Singleton
    fun provideRepository(): LaunchRepository {
        return mockk<LaunchRepository>()
    }

}