package com.sample.thespacedevs.di

import com.sample.repositories.LaunchRepository
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class RepoModule {
    @Provides
    @Singleton
    fun provideRepository(): com.sample.repositories.LaunchRepository {
        return mockk<com.sample.repositories.LaunchRepository>()
    }

}