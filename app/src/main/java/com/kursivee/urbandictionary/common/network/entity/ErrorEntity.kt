package com.kursivee.urbandictionary.common.network.entity

enum class ErrorId {
    GENERIC,
    OFFLINE
}

object ErrorConstants {
    const val OFFLINE_ERROR_CODE = 3000
}

/**
 * Just using a generic class to hold server error information.
 * Can add something like status code if you need to execute information
 * on a given range. e.g. 401 refresh auth
 */
data class ErrorEntity(
    val id: ErrorId = ErrorId.GENERIC
)
