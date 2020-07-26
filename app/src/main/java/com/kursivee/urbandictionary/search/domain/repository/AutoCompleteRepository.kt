package com.kursivee.urbandictionary.search.domain.repository

import com.kursivee.urbandictionary.common.network.alias.NetworkResponse
import com.kursivee.urbandictionary.search.domain.entity.AutoCompleteResult

interface AutoCompleteRepository {
    suspend fun getAutoCompleteResults(input: String): NetworkResponse<List<AutoCompleteResult>>
}
