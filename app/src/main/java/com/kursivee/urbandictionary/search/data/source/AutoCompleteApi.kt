package com.kursivee.urbandictionary.search.data.source

import com.kursivee.urbandictionary.search.data.source.dto.GetAutoCompleteResultsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AutoCompleteApi {
    @GET("/v0/autocomplete-extra")
    suspend fun getAutoCompleteResults(@Query("term") input: String): Response<GetAutoCompleteResultsResponse>
}
