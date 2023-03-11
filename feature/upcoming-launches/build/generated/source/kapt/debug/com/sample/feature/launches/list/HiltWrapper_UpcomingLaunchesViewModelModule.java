package com.sample.feature.launches.list;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.codegen.OriginatingElement;

@OriginatingElement(
    topLevelClass = UpcomingLaunchesViewModelModule.class
)
@InstallIn(ViewModelComponent.class)
@Module(
    includes = UpcomingLaunchesViewModelModule.class
)
public final class HiltWrapper_UpcomingLaunchesViewModelModule {
}
