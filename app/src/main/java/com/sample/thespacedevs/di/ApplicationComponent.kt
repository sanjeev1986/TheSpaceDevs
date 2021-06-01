package com.sample.thespacedevs.di

import android.app.Application
import android.content.Context
import com.sample.thespacedevs.TheSpaceDevApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        APIModule::class,
        PlatformModule::class,
        StorageModule::class,
        DispatchersModule::class,
        FeatureModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<TheSpaceDevApp> {
    @Component.Factory
    interface Builder : AndroidInjector.Factory<TheSpaceDevApp> {
    }
}
