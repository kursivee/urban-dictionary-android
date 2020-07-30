package com.kursivee.urbandictionary.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kursivee.urbandictionary.R
import com.kursivee.urbandictionary.common.util.debounce
import com.kursivee.urbandictionary.databinding.SearchFragmentBinding
import com.kursivee.urbandictionary.search.presentation.recycler.AutoCompleteResultsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    companion object {
        fun newInstance() =
            SearchFragment()
        const val DEBOUNCE_DELAY = 500L
    }

    private val vm: SearchViewModel by viewModels()
    private var autoCompleteResultsAdapter: AutoCompleteResultsAdapter? = null

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
        binding.rvAutocomplete.init()
        vm.state.observe(viewLifecycleOwner, Observer(::render))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
        (menu.findItem(R.id.search_menu).actionView as SearchView).init()
    }

    override fun onDestroyView() {
        nullableBinding = null
        autoCompleteResultsAdapter = null
        super.onDestroyView()
    }

    private fun RecyclerView.init() {
        autoCompleteResultsAdapter = AutoCompleteResultsAdapter { term ->
            val action = SearchFragmentDirections.actionSearchFragmentToResultsFragment(term)
            findNavController().navigate(action)
        }
        adapter = autoCompleteResultsAdapter
        layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    private fun SearchView.init() {
        val onQueryTextChange = debounce<String>(DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope) { input ->
            vm.getAutoCompleteResults(input)
        }
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                onQueryTextChange(newText ?: "")
                return true
            }
        })
    }

    private fun render(state: SearchState) {
        requireNotNull(autoCompleteResultsAdapter).submitList(state.autoCompleteResults)
    }
}
