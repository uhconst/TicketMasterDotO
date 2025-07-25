package com.uhc.lib.network.utils.data

/**
 * Default exception
 */
class DefaultException(
    val code: String = "",
    override val message: String = "Unexpected error"
) : Exception()
