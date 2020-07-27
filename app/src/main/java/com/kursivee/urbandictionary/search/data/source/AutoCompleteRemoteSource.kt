package com.kursivee.urbandictionary.search.data.source

import com.kursivee.urbandictionary.search.data.source.dto.GetAutoCompleteResultsResponse
import retrofit2.Response

class AutoCompleteRemoteSource(
    private val autoCompleteApi: AutoCompleteApi
) {
    suspend fun getAutoCompleteResults(input: String): Response<GetAutoCompleteResultsResponse> {
        return autoCompleteApi.getAutoCompleteResults(input)
    }
}
