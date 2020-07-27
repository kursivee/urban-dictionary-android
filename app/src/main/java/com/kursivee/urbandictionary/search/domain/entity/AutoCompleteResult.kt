package com.kursivee.urbandictionary.search.domain.entity

import java.util.UUID

data class AutoCompleteResult(
    val preview: String,
    val term: String,
    val id: UUID = UUID.randomUUID()
)
