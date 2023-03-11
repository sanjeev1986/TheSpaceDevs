package com.sample.base

interface UiStateMapper<M, R> {
    fun map(model: M): R
}
