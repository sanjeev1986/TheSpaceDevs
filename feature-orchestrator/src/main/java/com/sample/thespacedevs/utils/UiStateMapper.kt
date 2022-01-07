package com.sample.thespacedevs.utils

interface UiStateMapper<M, R> {
    fun map(model: M): R
}