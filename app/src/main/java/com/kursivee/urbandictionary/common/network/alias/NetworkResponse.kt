package com.kursivee.urbandictionary.common.network.alias

import arrow.core.Either
import com.kursivee.urbandictionary.common.network.entity.ErrorEntity

typealias NetworkResponse<T> = Either<ErrorEntity, T>
