package com.kursivee.urbandictionary.search.data.mapper

import arrow.core.Either
import com.kursivee.urbandictionary.common.ext.failure
import com.kursivee.urbandictionary.common.ext.success
import com.kursivee.urbandictionary.common.network.entity.ErrorEntity
import com.kursivee.urbandictionary.search.data.source.dto.AutoComplete
import com.kursivee.urbandictionary.search.data.source.dto.GetAutoCompleteResultsResponse
import com.kursivee.urbandictionary.search.domain.entity.AutoCompleteResult
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class AutoCompleteMappersTest {
    @Test
    fun `given GetAutoCompleteResultsResponse when response is successful then return mapped success response`() {
        val preview = "preview"
        val term = "term"
        val response = Response.success(
            GetAutoCompleteResultsResponse(
                listOf(
                    AutoComplete(preview, term)
                )
            )
        )
        val networkResponse = response.toNetworkResponse() as Either.Right
        val uuid = networkResponse.b[0].id
        assertEquals(networkResponse, Either.success(listOf(AutoCompleteResult(preview, term, uuid))))
    }

    @Test
    fun `given GetAutoCompleteResultsResponse when response is successful and body is empty then return mapped error`() {
        val response = Response.success<GetAutoCompleteResultsResponse>(null)
        assertEquals(response.toNetworkResponse(), Either.failure(ErrorEntity()))
    }

    @Test
    fun `given GetAutoCompleteResultsResponse when response is failure then return mapped error`() {
        val response = Response.success<GetAutoCompleteResultsResponse>(null)
        assertEquals(response.toNetworkResponse(), Either.failure(ErrorEntity()))
    }
}
