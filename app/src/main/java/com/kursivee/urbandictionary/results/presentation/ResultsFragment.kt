package com.kursivee.urbandictionary.results.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kursivee.urbandictionary.R
import com.kursivee.urbandictionary.common.view.SingleEvent
import com.kursivee.urbandictionary.common.view.recyclerview.EmptyListAdapter
import com.kursivee.urbandictionary.databinding.ResultsFragmentBinding
import com.kursivee.urbandictionary.results.presentation.recyclerview.ResultsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment : Fragment() {

    private var nullableBinding: ResultsFragmentBinding? = null
    private val binding: ResultsFragmentBinding
        get() = nullableBinding!!

    private val vm: ResultsViewModel by viewModels()
    private val args: ResultsFragmentArgs by navArgs()

    private var resultsAdapter: ResultsAdapter? = null
    private var emptyListAdapter: EmptyListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        nullableBinding = ResultsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvResults.init()
        vm.state.observe(viewLifecycleOwner, Observer(::render))
        vm.singleEventState.observe(viewLifecycleOwner, Observer(::onSingleEvent))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.results_options_menu, menu)
        menu.findItem(R.id.search_menu).setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_resultsFragment_to_searchFragment)
            true
        }
        menu.findItem(R.id.sort_menu).setOnMenuItemClickListener {
            showSortByDialog()
            true
        }
    }

    override fun onDestroyView() {
        nullableBinding = null
        resultsAdapter = null
        emptyListAdapter = null
        super.onDestroyView()
    }

    private fun showSortByDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.sort_by)
            .setItems(R.array.sort_array) { _, which ->
                vm.sortBy(which)
            }.show()
    }

    private fun RecyclerView.init() {
        resultsAdapter = ResultsAdapter { term ->
            val action = ResultsFragmentDirections.actionResultsFragmentSelf(term)
            findNavController().navigate(action)
        }
        emptyListAdapter = EmptyListAdapter()
        adapter = ConcatAdapter(
            emptyListAdapter,
            resultsAdapter
        )
        layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.srlResults.setOnRefreshListener {
            args.term?.let { term ->
                vm.getResults(term, true)
            } ?: run {
                binding.srlResults.isRefreshing = false
            }
        }
    }

    private fun render(resultsState: ResultsState) {
        requireNotNull(resultsAdapter).submitList(resultsState.results)
    }

    private fun onSingleEvent(singleEvent: SingleEvent<ResultsEvent>) {
        singleEvent.getContentIfNotHandled()?.let { event ->
            when (event) {
                is ResultsEvent.ErrorEvent ->
                    Toast.makeText(requireContext(), event.error.id.name, Toast.LENGTH_SHORT).show()
                is ResultsEvent.FetchCompleteEvent -> {
                    binding.srlResults.isRefreshing = false
                    binding.pbLoader.visibility = View.GONE
                    requireNotNull(emptyListAdapter).updateListSize(
                        vm.state.value!!.results.size
                    )
                }
                is ResultsEvent.FetchStartEvent ->
                    binding.pbLoader.visibility = View.VISIBLE
                is ResultsEvent.InitializeEvent -> {
                    args.term?.let { term ->
                        vm.getResults(term)
                    }
                }
            }
        }
    }
}
