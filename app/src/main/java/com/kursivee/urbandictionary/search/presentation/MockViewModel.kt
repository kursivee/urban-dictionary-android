package com.kursivee.urbandictionary.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kursivee.urbandictionary.search.domain.entity.AutoCompleteResult
import java.util.UUID

class MockViewModel : ViewModel() {

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
