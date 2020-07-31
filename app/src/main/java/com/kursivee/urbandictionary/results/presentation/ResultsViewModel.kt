package com.kursivee.urbandictionary.results.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursivee.urbandictionary.common.view.SingleEvent
import com.kursivee.urbandictionary.results.domain.entity.ResultEntity
import com.kursivee.urbandictionary.results.domain.usecase.GetResults
import kotlinx.coroutines.launch

class ResultsViewModel @ViewModelInject constructor(
    private val getResultsUseCase: GetResults,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mutableState = MutableLiveData<ResultsState>(ResultsState())
    val state: LiveData<ResultsState> = mutableState

    private val mutableSingleEventState = MutableLiveData<SingleEvent<ResultsEvent>>(
        SingleEvent(ResultsEvent.InitializeEvent)
    )
    val singleEventState: LiveData<SingleEvent<ResultsEvent>> = mutableSingleEventState

    fun getResults(term: String, isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (!isRefresh) {
                mutableSingleEventState.value = SingleEvent(ResultsEvent.FetchStartEvent)
            }
            getResultsUseCase(term).fold(
                { error ->
                    mutableSingleEventState.value = SingleEvent(ResultsEvent.ErrorEvent(error))
                },
                { results ->
                    mutableState.value = state.value?.copy(
                        results = results.sort(state.value?.sortType ?: SortType.THUMBS_UP)
                    )
                }
            )
            mutableSingleEventState.value = SingleEvent(ResultsEvent.FetchCompleteEvent)
        }
    }

    fun sortBy(i: Int) {
        val sortType = when (i) {
            0 -> SortType.THUMBS_UP
            else -> SortType.THUMBS_DOWN
        }
        if (sortType != state.value?.sortType) {
            mutableState.value = state.value?.copy(
                results = state.value?.results?.sort(sortType) ?: emptyList(),
                sortType = sortType
            )
        }
    }

    private fun List<ResultEntity>.sort(sortType: SortType): List<ResultEntity> {
        return when (sortType) {
            SortType.THUMBS_UP -> {
                sortedByDescending { result ->
                    result.thumbsUpCount
                }
            }
            SortType.THUMBS_DOWN -> {
                sortedByDescending { result ->
                    result.thumbsDownCount
                }
            }
        }
    }
}
