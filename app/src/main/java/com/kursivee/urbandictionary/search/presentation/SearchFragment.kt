package com.kursivee.urbandictionary.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kursivee.urbandictionary.R
import com.kursivee.urbandictionary.common.util.debounce
import com.kursivee.urbandictionary.databinding.SearchFragmentBinding
import com.kursivee.urbandictionary.search.presentation.recycler.AutoCompleteResultsAdapter

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() =
            SearchFragment()
        private const val DEBOUNCE_DELAY = 600L
    }

    private lateinit var viewModel: MockViewModel
    private lateinit var autoCompleteResultsAdapter: AutoCompleteResultsAdapter

    private var nullableBinding: SearchFragmentBinding? = null
    private val binding: SearchFragmentBinding
        get() = nullableBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        nullableBinding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MockViewModel::class.java)
        binding.rvAutocomplete.init()
        viewModel.get()
        viewModel.state.observe(
            viewLifecycleOwner,
            Observer { state ->
                autoCompleteResultsAdapter.submitList(state.autoCompleteResults)
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
        val item = menu.findItem(R.id.search_menu)
        val searchView = item.actionView as SearchView
        val onQueryTextChange = debounce<String>(DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope) {
            viewModel.get()
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(requireContext(), query ?: "", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) return true
                onQueryTextChange(newText)
                return true
            }
        })
    }

    override fun onDestroy() {
        nullableBinding = null
        super.onDestroy()
    }

    private fun RecyclerView.init() {
        autoCompleteResultsAdapter = AutoCompleteResultsAdapter()
        adapter = autoCompleteResultsAdapter
        layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }
}
