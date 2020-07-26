package com.kursivee.urbandictionary.search.domain.usecase

import arrow.core.Either
import arrow.core.success
import com.kursivee.urbandictionary.common.ext.failure
import com.kursivee.urbandictionary.common.ext.isFailure
import com.kursivee.urbandictionary.common.ext.isSuccess
import com.kursivee.urbandictionary.common.ext.success
import com.kursivee.urbandictionary.common.network.entity.ErrorEntity
import com.kursivee.urbandictionary.search.domain.entity.AutoCompleteResult
import com.kursivee.urbandictionary.search.domain.repository.AutoCompleteRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAutoCompleteResultsTest {

    private lateinit var getAutoCompleteResults: GetAutoCompleteResults
    private lateinit var autoCompleteRepository: AutoCompleteRepository

    @Before
    fun before() {
        autoCompleteRepository = mockk()
        getAutoCompleteResults = GetAutoCompleteResults(autoCompleteRepository)
    }

    @Test
    fun `given empty string when getting autocomplete results then return success and empty autocomplete result`() = runBlockingTest {
        val result = getAutoCompleteResults("")
        assertTrue(result.isSuccess())
        result.fold(
            {
                fail()
            },
            { list ->
                assertTrue(list.isEmpty())
            }
        )
    }

    @Test
    fun `given valid string when getting autocomplete results and successful then return success and autocomplete result`() = runBlocking {
        val mockData = listOf(
            AutoCompleteResult("", ""),
            AutoCompleteResult("", "")
        )
        coEvery {
            autoCompleteRepository.getAutoCompleteResults(any())
        } returns Either.success(mockData)
        val result = getAutoCompleteResults("valid string")

        assertTrue(result.isSuccess())
        result.fold(
            {
                fail()
            },
            { list ->
                assertTrue(list.containsAll(mockData))
            }
        )
    }

    @Test
    fun `given valid string when getting autocomplete results and failure then return error`() = runBlocking {
        coEvery {
            autoCompleteRepository.getAutoCompleteResults(any())
        } returns Either.failure(ErrorEntity())
        val result = getAutoCompleteResults("valid string")
        assertTrue(result.isFailure())
    }
}
