package com.sample.base

sealed class IOResult<out R> {
    data class Success<out R>(val result: R) : IOResult<R>()

    data class Failure<R>(val error: Exception, val fallback: R? = null) :
        IOResult<R>()
}
