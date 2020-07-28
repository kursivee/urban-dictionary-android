package com.kursivee.urbandictionary.search.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kursivee.urbandictionary.search.domain.entity.AutoCompleteResult
import java.util.UUID

class MockViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mutableState = MutableLiveData<SearchState>(SearchState())
    val state: LiveData<SearchState> = mutableState

    fun get() {
        val list = mutableListOf<AutoCompleteResult>()
        repeat(30) {
            list.add(
                AutoCompleteResult(UUID.randomUUID().toString(), UUID.randomUUID().toString())
            )
        }
        mutableState.value = state.value?.copy(autoCompleteResults = list.subList(0, (Math.random() * list.size).toInt()))
    }
}
