package com.example.o7employeeslist.ui.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.o7employeeslist.R
import com.example.o7employeeslist.databinding.FragmentAnalyticsBinding
import com.example.o7employeeslist.model.EmployeeModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class AnalyticsFragment : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnalyticsViewModel by viewModels() //Fragment lifecycle viewModel

    companion object {
        fun newInstance() = AnalyticsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData()
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { renderState(it) }
        }
    }

    private fun renderState(state: AnalyticsViewModel.AnalyticsState) {
        binding.progressBar.isVisible = false
        state.error?.let {
            Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
        }
        state.analytics?.let {
            binding.tvAverageAge.text = getString(R.string.years, it.averageAge.toString())
            binding.tvMedianAge.text = getString(R.string.years, it.medianAge.toString())
            binding.tvSalary.text = getString(R.string.salary_text, it.maxSalary.toString())
            binding.tvGender.text = getString(
                R.string.male_female,
                it.maleNumber.toString(),
                it.femaleNumber.toString()
            )
        }
    }
}
