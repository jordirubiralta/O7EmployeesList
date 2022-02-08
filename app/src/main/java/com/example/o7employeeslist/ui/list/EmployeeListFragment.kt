package com.example.o7employeeslist.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.o7employeeslist.databinding.FragmentEmployeeListBinding
import com.example.o7employeeslist.ui.headers.EmployeeHeadersActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EmployeeListFragment: Fragment() {

    private var _binding: FragmentEmployeeListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EmployeeListViewModel by viewModels() //Fragment lifecycle viewModel

    private lateinit var adapter: EmployeeListAdapter

    companion object {
        fun newInstance() = EmployeeListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEmployeeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupUI()
    }

    private fun setupObservers() = with(lifecycleScope) {
        launchWhenStarted {
            viewModel.state.collect { renderState(it) }
        }
    }

    private fun setupUI() {
        adapter = EmployeeListAdapter(onItemClick = ::onItemClick)
        binding.rvEmployees.layoutManager = LinearLayoutManager(context)
        binding.rvEmployees.adapter = adapter
        binding.progressBar.isVisible = true
        viewModel.getData()
    }

    private fun renderState(state: EmployeeListViewModel.EmployeeListState) {
        binding.progressBar.isVisible = false
        state.error?.let{
            Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
        }
        binding.tvEmptyList.isVisible = state.employees.isNullOrEmpty()
        adapter.update(state.employees)
    }

    private fun onItemClick(name: String) {
        requireActivity().let {
            EmployeeHeadersActivity.newInstance(it, name)
        }
    }

    fun updateData() {
        viewModel.getData()
    }

}
