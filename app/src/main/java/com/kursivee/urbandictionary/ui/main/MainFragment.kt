package com.kursivee.urbandictionary.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kursivee.urbandictionary.databinding.MainFragmentBinding
import com.kursivee.urbandictionary.search.domain.entity.AutoCompleteResult
import com.kursivee.urbandictionary.search.presentation.recycler.AutoCompleteResultsAdapter

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var autoCompleteResultsAdapter: AutoCompleteResultsAdapter

    private var nullableBinding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = nullableBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        nullableBinding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.rvAutocomplete.init(viewModel.get())
    }

    override fun onDestroy() {
        nullableBinding = null
        super.onDestroy()
    }

    private fun RecyclerView.init(list: List<AutoCompleteResult>) {
        autoCompleteResultsAdapter = AutoCompleteResultsAdapter()
        adapter = autoCompleteResultsAdapter
        layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        autoCompleteResultsAdapter.submitList(list.toMutableList())
    }
}
