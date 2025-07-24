package com.uhc.domain.events.exception

/**
 * Default exception
 */
class DefaultException(
    val code: String = "",
    override val message: String = "Unexpected error"
) : Exception()