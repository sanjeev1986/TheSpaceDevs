// Generated by Dagger (https://dagger.dev).
package com.sample.feature.launches.list;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class UpcomingLaunchesViewModel_Factory implements Factory<UpcomingLaunchesViewModel> {
  private final Provider<LaunchListMapper> launchListMapperProvider;

  private final Provider<LoadingMapper> loadingMapperProvider;

  private final Provider<ErrorMapper> errorMapperProvider;

  private final Provider<LaunchRepository> repositoryProvider;

  public UpcomingLaunchesViewModel_Factory(Provider<LaunchListMapper> launchListMapperProvider,
      Provider<LoadingMapper> loadingMapperProvider, Provider<ErrorMapper> errorMapperProvider,
      Provider<LaunchRepository> repositoryProvider) {
    this.launchListMapperProvider = launchListMapperProvider;
    this.loadingMapperProvider = loadingMapperProvider;
    this.errorMapperProvider = errorMapperProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpcomingLaunchesViewModel get() {
    return newInstance(launchListMapperProvider.get(), loadingMapperProvider.get(), errorMapperProvider.get(), repositoryProvider.get());
  }

  public static UpcomingLaunchesViewModel_Factory create(
      Provider<LaunchListMapper> launchListMapperProvider,
      Provider<LoadingMapper> loadingMapperProvider, Provider<ErrorMapper> errorMapperProvider,
      Provider<LaunchRepository> repositoryProvider) {
    return new UpcomingLaunchesViewModel_Factory(launchListMapperProvider, loadingMapperProvider, errorMapperProvider, repositoryProvider);
  }

  public static UpcomingLaunchesViewModel newInstance(LaunchListMapper launchListMapper,
      LoadingMapper loadingMapper, ErrorMapper errorMapper, LaunchRepository repository) {
    return new UpcomingLaunchesViewModel(launchListMapper, loadingMapper, errorMapper, repository);
  }
}
