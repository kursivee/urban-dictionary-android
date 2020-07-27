package com.kursivee.urbandictionary.search.presentation

import com.kursivee.urbandictionary.common.network.entity.ErrorEntity
import com.kursivee.urbandictionary.search.domain.entity.AutoCompleteResult

data class SearchState(
    val autoCompleteResults: List<AutoCompleteResult> = emptyList()
)

sealed class SearchEvent {
    class ErrorEvent(private val error: ErrorEntity) : SearchEvent()
}
