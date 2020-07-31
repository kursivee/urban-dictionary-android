package com.kursivee.urbandictionary.search.data.source

import com.kursivee.urbandictionary.common.network.entity.ErrorConstants.OFFLINE_ERROR_CODE
import com.kursivee.urbandictionary.search.data.source.dto.GetAutoCompleteResultsResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class AutoCompleteRemoteSource @Inject constructor(
    private val autoCompleteApi: AutoCompleteApi
) {
    suspend fun getAutoCompleteResults(input: String): Response<GetAutoCompleteResultsResponse> {
        return kotlin.runCatching {
            autoCompleteApi.getAutoCompleteResults(input)
        }.getOrDefault(Response.error(OFFLINE_ERROR_CODE, "".toResponseBody()))
    }
}
