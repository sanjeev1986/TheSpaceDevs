package com.sample.thespacedevs.di

import android.content.Context
import com.sample.thespacedevs.MainActivity
import com.sample.thespacedevs.TheSpaceDevApp
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class FeatureModule {

    @Singleton
    @Binds
    abstract fun provideContext(app: TheSpaceDevApp): Context

    @ContributesAndroidInjector
    abstract fun injectMainActivity(): MainActivity

   /* @ContributesAndroidInjector
    abstract fun injectLaunchDetailsFragment(): LaunchDetailsFragment*/
}