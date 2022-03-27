package com.sample.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class AppDispatchers(val io: CoroutineDispatcher = Dispatchers.IO, val main: CoroutineDispatcher = Dispatchers.Main)