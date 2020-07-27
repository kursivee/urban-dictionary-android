package com.kursivee.urbandictionary.search.data.source.dto

data class GetAutoCompleteResultsResponse(
    val results: List<AutoComplete>
)

data class AutoComplete(
    val preview: String,
    val term: String
)
