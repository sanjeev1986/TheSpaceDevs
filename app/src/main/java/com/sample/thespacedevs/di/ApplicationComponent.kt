package com.sample.thespacedevs.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        APIModule::class,
        PlatformModule::class,
        StorageModule::class,
        DispatchersModule::class
    ]
)
interface ApplicationComponent : DaggerComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): ApplicationComponent
    }
}
