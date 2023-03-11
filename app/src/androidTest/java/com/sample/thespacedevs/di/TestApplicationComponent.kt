package com.sample.thespacedevs.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestApiModule::class,
        TestPlatformModule::class,
        GsonModuleTest::class,
        StorageModuleTest::class,
        DispatchersModuleTest::class,
        RepoModule::class,
    ],
)
interface TestApplicationComponent : DaggerComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): TestApplicationComponent
    }

    fun inject(fragment: UpcomingLaunchesFragmentTest)
}
