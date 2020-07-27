package com.kursivee.urbandictionary.search.data.repository

import com.kursivee.urbandictionary.common.ext.isFailure
import com.kursivee.urbandictionary.common.ext.isSuccess
import com.kursivee.urbandictionary.search.data.source.AutoCompleteRemoteSource
import com.kursivee.urbandictionary.search.data.source.dto.AutoComplete
import com.kursivee.urbandictionary.search.data.source.dto.GetAutoCompleteResultsResponse
import com.kursivee.urbandictionary.search.domain.repository.AutoCompleteRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class AutoCompleteRepositoryImplTest {

    private lateinit var autoCompleteRepository: AutoCompleteRepository
    private lateinit var autoCompleteRemoteSource: AutoCompleteRemoteSource

    @Before
    fun before() {
        autoCompleteRemoteSource = mockk()
        autoCompleteRepository = AutoCompleteRepositoryImpl(autoCompleteRemoteSource)
    }

    @Test
    fun `given input when getting auto complete results and successful then returns success response`() = runBlockingTest {
        val mockResults = listOf<AutoComplete>()
        coEvery {
            autoCompleteRemoteSource.getAutoCompleteResults(any())
        } returns Response.success(GetAutoCompleteResultsResponse(mockResults))
        val response = autoCompleteRepository.getAutoCompleteResults("valid string")
        assertTrue(response.isSuccess())
        response.map { list ->
            assertTrue(list.size == mockResults.size)
        }
    }

    @Test
    fun `given input when getting auto complete results and failure then returns error response`() = runBlockingTest {
        coEvery {
            autoCompleteRemoteSource.getAutoCompleteResults(any())
        } returns Response.error(500, "".toResponseBody())
        val response = autoCompleteRepository.getAutoCompleteResults("valid string")
        assertTrue(response.isFailure())
    }

    @Test
    fun `given input when getting auto complete results and successful and body is null then returns error response`() = runBlockingTest {
        coEvery {
            autoCompleteRemoteSource.getAutoCompleteResults(any())
        } returns Response.success(null)
        val response = autoCompleteRepository.getAutoCompleteResults("valid string")
        assertTrue(response.isFailure())
    }
}
