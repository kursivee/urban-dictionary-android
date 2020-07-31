package com.kursivee.urbandictionary.results.data.mapper

import arrow.core.Either
import com.kursivee.urbandictionary.common.ext.failure
import com.kursivee.urbandictionary.common.ext.success
import com.kursivee.urbandictionary.common.network.alias.NetworkResponse
import com.kursivee.urbandictionary.common.network.entity.ErrorConstants.OFFLINE_ERROR_CODE
import com.kursivee.urbandictionary.common.network.entity.ErrorEntity
import com.kursivee.urbandictionary.common.network.entity.ErrorId
import com.kursivee.urbandictionary.results.data.source.dto.CachedResult
import com.kursivee.urbandictionary.results.data.source.dto.GetResultsResponse
import com.kursivee.urbandictionary.results.data.source.dto.Result
import com.kursivee.urbandictionary.results.domain.entity.ResultEntity
import retrofit2.Response
import timber.log.Timber

/**
 * I'll probably get rid of the Response<T>.toNetworkResponse classes
 * and just keep the S -> T Mappers. There's too much edge casing and I don't
 * want to pass a onSuccess, onError lambda since I feel that increases the scope of
 * mapping.
 */

fun Response<GetResultsResponse>.toNetworkResponse(): NetworkResponse<List<ResultEntity>> {
    return if (isSuccessful) {
        body()?.list?.toResultEntities()?.let { list ->
            Either.success(list)
        } ?: Either.failure(ErrorEntity())
    } else {
        Timber.e(errorBody()?.string())
        val id = if (code() == OFFLINE_ERROR_CODE) ErrorId.OFFLINE else ErrorId.GENERIC
        Either.failure(ErrorEntity(id))
    }
}

fun List<CachedResult>.toNetworkResponse(): NetworkResponse<List<ResultEntity>> {
    val list = map { result ->
        ResultEntity(
            id = result.id,
            author = result.author,
            word = result.word,
            definition = result.definition,
            example = result.example,
            thumbsDownCount = result.thumbsDownCount,
            thumbsUpCount = result.thumbsUpCount,
            writtenOn = result.writtenOn
        )
    }
    return Either.success(list)
}

fun List<ResultEntity>.toCachedResults(term: String): List<CachedResult> {
    return map { result ->
        CachedResult(
            id = result.id,
            author = result.author,
            word = result.word,
            definition = result.definition,
            example = result.example,
            thumbsDownCount = result.thumbsDownCount,
            thumbsUpCount = result.thumbsUpCount,
            writtenOn = result.writtenOn,
            searchTerm = term
        )
    }
}

fun List<Result>.toResultEntities(): List<ResultEntity> {
    return map { result ->
        ResultEntity(
            id = result.defid,
            author = result.author,
            word = result.word,
            definition = result.definition,
            example = result.example,
            thumbsDownCount = result.thumbsDown,
            thumbsUpCount = result.thumbsUp,
            writtenOn = result.writtenOn
        )
    }
}
