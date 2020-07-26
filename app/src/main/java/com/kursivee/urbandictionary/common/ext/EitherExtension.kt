package com.kursivee.urbandictionary.common.ext

import arrow.core.Either
import com.kursivee.urbandictionary.common.network.alias.NetworkResponse

/**
 * Extension function that renames left to failure
 */
fun <L> Either.Companion.failure(left: L): Either<L, Nothing> = Either.Left(left)

/**
 * Utility function that renames right to success
 */
fun <R> Either.Companion.success(right: R): Either<Nothing, R> = Either.Right(right)

/**
 * Extension function that renames isRight to isSuccess
 */
fun <T> NetworkResponse<T>.isSuccess(): Boolean = isRight()

/**
 * Extension function that renames isRight to isSuccess
 */
fun <T> NetworkResponse<T>.isFailure(): Boolean = isLeft()
