package com.kursivee.urbandictionary.search.data.source

import com.kursivee.urbandictionary.search.data.source.dto.GetAutoCompleteResultsResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class AutoCompleteRemoteSource @Inject constructor(
    private val autoCompleteApi: AutoCompleteApi
) {
    suspend fun getAutoCompleteResults(input: String): Response<GetAutoCompleteResultsResponse> {
        return autoCompleteApi.getAutoCompleteResults(input)
    }
}
