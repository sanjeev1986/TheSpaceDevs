package com.sample.thespacedevs.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Singleton
data class AppDispatchers(val io: CoroutineDispatcher = Dispatchers.IO, val main: CoroutineDispatcher = Dispatchers.Main)