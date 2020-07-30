package com.kursivee.urbandictionary.results.data.source

import com.kursivee.urbandictionary.results.data.source.dto.GetResultsResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class GetResultsRemoteSource @Inject constructor(
    private val getResultsApi: GetResultsApi
) {
    suspend fun getResults(term: String): Response<GetResultsResponse> {
        return getResultsApi.getResults(term)
    }
}
