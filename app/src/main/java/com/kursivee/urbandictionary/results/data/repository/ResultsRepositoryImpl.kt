package com.kursivee.urbandictionary.results.data.repository

import arrow.core.Either
import com.kursivee.urbandictionary.common.ext.failure
import com.kursivee.urbandictionary.common.ext.success
import com.kursivee.urbandictionary.common.network.alias.NetworkResponse
import com.kursivee.urbandictionary.common.network.entity.ErrorConstants.OFFLINE_ERROR_CODE
import com.kursivee.urbandictionary.common.network.entity.ErrorEntity
import com.kursivee.urbandictionary.common.network.entity.ErrorId
import com.kursivee.urbandictionary.results.data.mapper.toCachedResults
import com.kursivee.urbandictionary.results.data.mapper.toNetworkResponse
import com.kursivee.urbandictionary.results.data.mapper.toResultEntities
import com.kursivee.urbandictionary.results.data.source.GetResultsLocalSource
import com.kursivee.urbandictionary.results.data.source.GetResultsRemoteSource
import com.kursivee.urbandictionary.results.domain.entity.ResultEntity
import com.kursivee.urbandictionary.results.domain.repository.ResultsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ResultsRepositoryImpl @Inject constructor(
    private val getResultsRemoteSource: GetResultsRemoteSource,
    private val getResultsLocalSource: GetResultsLocalSource
) : ResultsRepository {
    override suspend fun getResults(term: String): NetworkResponse<List<ResultEntity>> {
        val cachedResult = getResultsLocalSource.getResults(term)
        if (!cachedResult.isNullOrEmpty()) return cachedResult.toNetworkResponse()
        val results = getResultsRemoteSource.getResults(term)
        var list: List<ResultEntity>? = null
        if (results.isSuccessful) {
            list = results.body()?.list?.toResultEntities()
            list?.toCachedResults(term)?.let { cachedResults ->
                getResultsLocalSource.insertResults(cachedResults)
            }
        }
        return list?.let { r ->
            Either.success(r)
        } ?: run {
            withContext(Dispatchers.IO) {
                Timber.e(results.errorBody()?.string())
                val id = if (results.code() == OFFLINE_ERROR_CODE) ErrorId.OFFLINE else ErrorId.GENERIC
                Either.failure(ErrorEntity(id))
            }
        }
    }

    override suspend fun getRandomResults(): NetworkResponse<List<ResultEntity>> {
        return getResultsRemoteSource.getRandomResults().toNetworkResponse()
    }
}
