package com.kursivee.urbandictionary.results.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursivee.urbandictionary.common.view.SingleEvent
import com.kursivee.urbandictionary.results.domain.usecase.GetResults
import kotlinx.coroutines.launch

class ResultsViewModel @ViewModelInject constructor(
    private val getResultsUseCase: GetResults,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mutableState = MutableLiveData<ResultsState>(ResultsState())
    val state: LiveData<ResultsState> = mutableState

    private val mutableSingleEventState = MutableLiveData<SingleEvent<ResultsEvent>>()
    val singleEventState: LiveData<SingleEvent<ResultsEvent>> = mutableSingleEventState

    fun getResults(term: String) {
        viewModelScope.launch {
            getResultsUseCase(term).fold(
                { error ->
                    mutableSingleEventState.value = SingleEvent(ResultsEvent.ErrorEvent(error))
                },
                { results ->
                    mutableState.value = state.value?.copy(results = results)
                    mutableSingleEventState.value = SingleEvent(ResultsEvent.FetchCompleteEvent)
                }
            )
        }
    }
}
