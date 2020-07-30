package com.kursivee.urbandictionary.results.presentation

import com.kursivee.urbandictionary.common.network.entity.ErrorEntity
import com.kursivee.urbandictionary.results.domain.entity.ResultEntity

data class ResultsState(
    val results: List<ResultEntity> = emptyList()
)

sealed class ResultsEvent {
    class ErrorEvent(private val error: ErrorEntity) : ResultsEvent()
}
