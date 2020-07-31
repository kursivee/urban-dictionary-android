package com.kursivee.urbandictionary.results.data.source

import com.kursivee.urbandictionary.common.network.entity.ErrorConstants.OFFLINE_ERROR_CODE
import com.kursivee.urbandictionary.results.data.source.dto.GetResultsResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class GetResultsRemoteSource @Inject constructor(
    private val getResultsApi: GetResultsApi
) {
    suspend fun getResults(term: String): Response<GetResultsResponse> {
        return kotlin.runCatching {
            getResultsApi.getResults(term)
        }.getOrDefault(Response.error(OFFLINE_ERROR_CODE, "".toResponseBody()))
    }

    suspend fun getRandomResults(): Response<GetResultsResponse> {
        return kotlin.runCatching {
            getResultsApi.getRandomResults()
        }.getOrDefault(Response.error(OFFLINE_ERROR_CODE, "".toResponseBody()))
    }
}
