package com.kursivee.urbandictionary.ui.main

import androidx.lifecycle.ViewModel
import com.kursivee.urbandictionary.search.domain.entity.AutoCompleteResult

class MainViewModel : ViewModel() {
    fun get(): List<AutoCompleteResult> {
        val longterm = "/Users/kursivee/dev/urban-dictionary/app/src/main/" +
            "java/com/kursivee/urbandictionary/ui/main/MainViewModel.kt" +
            " /Users/kursivee/dev/urban-dictionary/app/src/main/" +
            "java/com/kursivee/urbandictionary/ui/main/MainViewModel.kt"
        return listOf(
            AutoCompleteResult("preview 1", "term1"),
            AutoCompleteResult("preview 2", "term2"),
            AutoCompleteResult("preview 3", "term3"),
            AutoCompleteResult("preview 4", "term4"),
            AutoCompleteResult(
                term = "term5",
                preview = longterm
            )
        )
    }
}
