package com.kursivee.urbandictionary.common.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kursivee.urbandictionary.R
import com.kursivee.urbandictionary.databinding.EmptyListItemBinding

class EmptyListAdapter(private var listSize: Int = 1) : RecyclerView.Adapter<EmptyListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyListViewHolder {
        val binding = EmptyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmptyListViewHolder(binding)
    }

    override fun getItemCount(): Int = if (listSize > 0) 0 else 1

    fun updateListSize(listSize: Int) {
        this.listSize = listSize
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: EmptyListViewHolder, position: Int) {
        // noop
    }

    override fun getItemViewType(position: Int): Int = R.layout.empty_list_item
}
