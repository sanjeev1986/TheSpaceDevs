package com.sample.base

//@OpenForTesting
sealed class RepoResult<out R> {
    //@OpenForTesting
    data class Success<out R>(val result: R) : RepoResult<R>()

    //@OpenForTesting
    data class Failure<R>(val error: Exception, val fallback: R? = null) :
        RepoResult<R>()
}