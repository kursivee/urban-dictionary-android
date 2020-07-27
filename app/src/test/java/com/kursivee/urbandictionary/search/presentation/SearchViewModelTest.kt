package com.kursivee.urbandictionary.search.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import com.kursivee.urbandictionary.common.ext.failure
import com.kursivee.urbandictionary.common.ext.success
import com.kursivee.urbandictionary.common.network.entity.ErrorEntity
import com.kursivee.urbandictionary.search.domain.entity.AutoCompleteResult
import com.kursivee.urbandictionary.search.domain.usecase.GetAutoCompleteResults
import com.kursivee.urbandictionary.util.MainCoroutineScopeRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var getAutoCompleteResults: GetAutoCompleteResults

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineScopeRule()

    @Before
    fun before() {
        getAutoCompleteResults = mockk()
        searchViewModel = SearchViewModel(getAutoCompleteResults)
    }

    @Test
    fun `given valid input when success response then update list of results`() = runBlockingTest {
        val mockResult = AutoCompleteResult("", "")
        coEvery { getAutoCompleteResults.invoke(any()) } returns Either.success(
            listOf(
                mockResult
            )
        )
        searchViewModel.state.observeForever {}
        searchViewModel.getAutoCompleteResults("valid string")
        assertTrue(searchViewModel.state.value?.autoCompleteResults?.size == 1)
        assertEquals(mockResult, searchViewModel.state.value?.autoCompleteResults?.get(0))
    }

    @Test
    fun `given valid input when error response then update error state`() = runBlockingTest {
        coEvery { getAutoCompleteResults.invoke(any()) } returns Either.failure(ErrorEntity())
        searchViewModel.singleEventState.observeForever {}
        searchViewModel.getAutoCompleteResults("valid string")
        assertFalse(searchViewModel.singleEventState.value?.hasBeenHandled!!)
    }
}
