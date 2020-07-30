package com.kursivee.urbandictionary.search.presentation.recycler

import android.text.SpannableStringBuilder
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import com.kursivee.urbandictionary.databinding.AutoCompleteResultItemBinding
import com.kursivee.urbandictionary.search.domain.entity.AutoCompleteResult

class AutoCompleteResultsViewHolder(
    private val binding: AutoCompleteResultItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(autoCompleteResult: AutoCompleteResult, onClick: () -> Unit) {
        val result = SpannableStringBuilder()
            .bold { append(autoCompleteResult.term) }
            .append(" - ")
            .append(autoCompleteResult.preview)
        binding.tvAutocompleteResult.text = result
        binding.tvAutocompleteResult.setOnClickListener {
            onClick()
        }
    }
}
