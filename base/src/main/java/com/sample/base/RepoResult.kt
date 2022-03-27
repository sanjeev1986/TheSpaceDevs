package com.sample.base

sealed class RepoResult<out R> {
    data class Success<out R>(val result: R) : RepoResult<R>()

    data class Failure<R>(val error: Exception, val fallback: R? = null) :
        RepoResult<R>()
}