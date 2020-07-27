package com.kursivee.urbandictionary.search.data.source

import com.kursivee.urbandictionary.search.data.source.dto.GetAutoCompleteResultsResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class AutoCompleteRemoteSourceTest {

    private lateinit var autoCompleteRemoteSource: AutoCompleteRemoteSource
    private lateinit var autoCompleteApi: AutoCompleteApi

    @Before
    fun before() {
        autoCompleteApi = mockk()
        autoCompleteRemoteSource = AutoCompleteRemoteSource(autoCompleteApi)
    }

    @Test
    fun `given string when getting auto complete results then return response`() = runBlockingTest {
        val mockResponse: Response<GetAutoCompleteResultsResponse> = mockk()
        coEvery { autoCompleteApi.getAutoCompleteResults(any()) } returns mockResponse
        val response = autoCompleteRemoteSource.getAutoCompleteResults("")
        assertEquals(mockResponse, response)
    }
}
