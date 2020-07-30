package com.kursivee.urbandictionary.results.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kursivee.urbandictionary.databinding.ResultItemBinding
import com.kursivee.urbandictionary.results.domain.entity.ResultEntity

class ResultsAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<ResultEntity, ResultsViewHolder>(ResultsDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        val binding = ResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}
