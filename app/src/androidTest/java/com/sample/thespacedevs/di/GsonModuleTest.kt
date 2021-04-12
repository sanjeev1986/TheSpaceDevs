package com.sample.thespacedevs.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GsonModuleTest {
    @Singleton
    @Provides
    fun provoideGson() = Gson()
}