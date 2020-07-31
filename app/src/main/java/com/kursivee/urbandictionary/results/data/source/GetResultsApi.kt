package com.kursivee.urbandictionary.results.data.source

import com.kursivee.urbandictionary.results.data.source.dto.GetResultsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetResultsApi {
    @GET("/v0/define")
    suspend fun getResults(@Query("term") term: String): Response<GetResultsResponse>

    @GET("/v0/random")
    suspend fun getRandomResults(): Response<GetResultsResponse>
}
