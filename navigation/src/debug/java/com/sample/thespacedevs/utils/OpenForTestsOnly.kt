package com.sample.thespacedevs.utils

@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class OpenClass

/**
 * Opens classes for testing on Debug builds
 */
@OpenClass
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting
