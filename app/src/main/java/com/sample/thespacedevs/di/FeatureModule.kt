package com.sample.thespacedevs.di

import android.content.Context
import com.google.gson.Gson
import com.sample.feature.launches.UpcomingLaunchesFragment
import com.sample.thespacedevs.TheSpaceDevApp
import com.sample.thespacedevs.feature.spacecrafts.SpacecraftListFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class FeatureModule {

    @Singleton
    @Binds
    abstract fun provideContext(app: TheSpaceDevApp): Context

    @ContributesAndroidInjector
    abstract fun injectUpcomingLaunchesFragment(): UpcomingLaunchesFragment

    @ContributesAndroidInjector
    abstract fun injectSpacecraftListFragment(): SpacecraftListFragment
}