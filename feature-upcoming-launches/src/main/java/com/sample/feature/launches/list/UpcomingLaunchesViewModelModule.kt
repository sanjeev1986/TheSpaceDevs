package com.sample.feature.launches.list

import com.sample.platform.hardware.ConnectivityApiManager
import com.sample.thespacedevs.services.launch.LaunchApi
import com.sample.platform.storage.InMemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
internal object UpcomingLaunchesViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideSpacecraftRepository(
        connectivityApiManager: ConnectivityApiManager,
        inMemoryCache: InMemoryCache,
        launchApi: LaunchApi
    ): LaunchRepository {
        return LaunchRepository(connectivityApiManager, inMemoryCache, launchApi)
    }

    @Provides
    @ViewModelScoped
    fun provideLaunchListMapper(): LaunchListMapper {
        return LaunchListMapper()
    }

    @Provides
    @ViewModelScoped
    fun provideLoadingMapper(): LoadingMapper {
        return LoadingMapper()
    }

    @Provides
    @ViewModelScoped
    fun provideErrorMapper(): ErrorMapper {
        return ErrorMapper()
    }
}
