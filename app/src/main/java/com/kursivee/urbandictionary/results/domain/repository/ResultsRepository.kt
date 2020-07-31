package com.kursivee.urbandictionary.results.domain.repository

import com.kursivee.urbandictionary.common.network.alias.NetworkResponse
import com.kursivee.urbandictionary.results.domain.entity.ResultEntity

interface ResultsRepository {
    suspend fun getResults(term: String): NetworkResponse<List<ResultEntity>>
    suspend fun getRandomResults(): NetworkResponse<List<ResultEntity>>
}
