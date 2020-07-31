package com.kursivee.urbandictionary.results.presentation

import com.kursivee.urbandictionary.common.network.entity.ErrorEntity
import com.kursivee.urbandictionary.results.domain.entity.ResultEntity

data class ResultsState(
    val results: List<ResultEntity> = emptyList(),
    val sortType: SortType = SortType.THUMBS_UP
)

sealed class ResultsEvent {
    class ErrorEvent(val error: ErrorEntity) : ResultsEvent()
    object FetchCompleteEvent : ResultsEvent()
    object FetchStartEvent : ResultsEvent()
    object InitializeEvent : ResultsEvent()
}

enum class SortType {
    THUMBS_UP,
    THUMBS_DOWN
}
