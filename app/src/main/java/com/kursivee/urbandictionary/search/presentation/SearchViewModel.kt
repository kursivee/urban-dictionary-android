package com.kursivee.urbandictionary.search.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursivee.urbandictionary.common.view.SingleEvent
import com.kursivee.urbandictionary.search.domain.usecase.GetAutoCompleteResults
import kotlinx.coroutines.launch

class SearchViewModel @ViewModelInject constructor(
    private val getAutoCompleteResultsUseCase: GetAutoCompleteResults,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val mutableState = MutableLiveData<SearchState>(SearchState())
    val state: LiveData<SearchState> = mutableState

    private val mutableSingleEventState = MutableLiveData<SingleEvent<SearchEvent>>()
    val singleEventState: LiveData<SingleEvent<SearchEvent>> = mutableSingleEventState

    fun getAutoCompleteResults(input: String) {
        viewModelScope.launch {
            getAutoCompleteResultsUseCase(input).fold(
                { error ->
                    mutableSingleEventState.value = SingleEvent(SearchEvent.ErrorEvent(error))
                },
                { autoCompleteResults ->
                    mutableState.value = state.value?.copy(autoCompleteResults = autoCompleteResults)
                }
            )
        }
    }
}
