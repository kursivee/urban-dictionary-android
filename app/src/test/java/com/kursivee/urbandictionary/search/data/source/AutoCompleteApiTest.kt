package com.kursivee.urbandictionary.search.data.source

import com.kursivee.urbandictionary.common.network.HttpClientProvider
import com.kursivee.urbandictionary.search.data.source.dto.AutoComplete
import com.kursivee.urbandictionary.search.data.source.dto.GetAutoCompleteResultsResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

class AutoCompleteApiTest {

    private val mockWebServer = MockWebServer()
    private val retrofit = HttpClientProvider.provideRetrofit(mockWebServer.url("/").toString())
    private lateinit var autoCompleteApi: AutoCompleteApi

    @Before
    fun before() {
        autoCompleteApi = retrofit.create(AutoCompleteApi::class.java)
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

    @Test
    fun `given string when getting auto complete response then return response`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(File("src/test/res/AutoCompleteResultResponse.json").readText())
        mockWebServer.enqueue(mockResponse)
        val response = autoCompleteApi.getAutoCompleteResults("aw")
        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/v0/autocomplete-extra?term=aw", request.path)
        val expectedResponse = GetAutoCompleteResultsResponse(listOf(AutoComplete("a", "b")))
        assertEquals(expectedResponse, response.body())
    }
}
