package com.kursivee.urbandictionary.results.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.kursivee.urbandictionary.results.domain.entity.ResultEntity

object ResultsDiffCallback : DiffUtil.ItemCallback<ResultEntity>() {
    override fun areItemsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean {
        return oldItem == newItem
    }
}
