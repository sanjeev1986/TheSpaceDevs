package com.sample.thespacedevs.feature.vehicles.list

import com.sample.platform.hardware.ConnectivityApiManager
import com.sample.thespacedevs.feature.vehicles.SpacecraftRepository
import com.sample.thespacedevs.services.spacecraft.SpacecraftApi
import com.sample.platform.storage.InMemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
internal object SpaceCraftsViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideSpacecraftRepository(
        connectivityApiManager: ConnectivityApiManager,
        inMemoryCache: InMemoryCache,
        spacecraftApi: SpacecraftApi
    ): SpacecraftRepository {
        return SpacecraftRepository(connectivityApiManager, inMemoryCache, spacecraftApi)
    }
}