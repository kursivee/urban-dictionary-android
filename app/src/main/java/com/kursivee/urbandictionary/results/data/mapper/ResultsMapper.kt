package com.kursivee.urbandictionary.results.data.mapper

import arrow.core.Either
import com.kursivee.urbandictionary.common.ext.failure
import com.kursivee.urbandictionary.common.ext.success
import com.kursivee.urbandictionary.common.network.alias.NetworkResponse
import com.kursivee.urbandictionary.common.network.entity.ErrorEntity
import com.kursivee.urbandictionary.results.data.source.dto.GetResultsResponse
import com.kursivee.urbandictionary.results.domain.entity.ResultEntity
import retrofit2.Response

fun Response<GetResultsResponse>.toNetworkResponse(): NetworkResponse<List<ResultEntity>> {
    return if (isSuccessful) {
        val list = body()?.list?.map { result ->
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
        if (list != null) {
            Either.success(list)
        } else {
            Either.failure(ErrorEntity())
        }
    } else {
        Either.failure(ErrorEntity())
    }
}
