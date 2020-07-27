package com.kursivee.urbandictionary.search.presentation.recycler

import androidx.recyclerview.widget.DiffUtil
import com.kursivee.urbandictionary.search.domain.entity.AutoCompleteResult

object AutoCompleteResultsDiffCallback : DiffUtil.ItemCallback<AutoCompleteResult>() {
    override fun areItemsTheSame(
        oldItem: AutoCompleteResult,
        newItem: AutoCompleteResult
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AutoCompleteResult,
        newItem: AutoCompleteResult
    ): Boolean {
        return oldItem == newItem
    }
}
