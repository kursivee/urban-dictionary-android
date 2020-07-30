package com.kursivee.urbandictionary.results.data.repository

import com.kursivee.urbandictionary.common.network.alias.NetworkResponse
import com.kursivee.urbandictionary.results.data.mapper.toNetworkResponse
import com.kursivee.urbandictionary.results.data.source.GetResultsRemoteSource
import com.kursivee.urbandictionary.results.domain.entity.ResultEntity
import com.kursivee.urbandictionary.results.domain.repository.ResultsRepository
import javax.inject.Inject

class ResultsRepositoryImpl @Inject constructor(
    private val getResultsRemoteSource: GetResultsRemoteSource
) : ResultsRepository {
    override suspend fun getResults(term: String): NetworkResponse<List<ResultEntity>> {
        return getResultsRemoteSource.getResults(term).toNetworkResponse()
    }
}
