package com.kursivee.urbandictionary.results.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.kursivee.urbandictionary.databinding.ResultsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment : Fragment() {

    private var nullableBinding: ResultsFragmentBinding? = null
    private val binding: ResultsFragmentBinding
        get() = nullableBinding!!

    private val vm: ResultsViewModel by viewModels()
    private val args: ResultsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nullableBinding = ResultsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm.getResults(args.term)
        vm.state.observe(viewLifecycleOwner, Observer(::render))
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }

    private fun render(resultsState: ResultsState) {
        binding.tvTxt.text = resultsState.results.joinToString(", ")
    }
}
