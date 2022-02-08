package com.example.o7employeeslist.ui.headers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.o7employeeslist.databinding.ActivityEmployeeHeadersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class EmployeeHeadersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeHeadersBinding

    private val viewModel by viewModels<EmployeeHeadersViewModel>()

    companion object {

        const val EMPLOYEE_NAME = "employee_name"

        fun newInstance(activity: Activity, name: String ) {
            val intent = Intent(activity, EmployeeHeadersActivity::class.java)
            intent.putExtra(EMPLOYEE_NAME, name)
            activity.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeHeadersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupUI()
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { renderState(it) }
        }
    }

    private fun setupUI() {
        binding.rvHeaders.isVisible = false
        binding.progressBar.isVisible = true
        intent.extras?.getString(EMPLOYEE_NAME)?.let {
            viewModel.getProfileInformation(it)
        }
    }

    private fun renderState(state: EmployeeHeadersViewModel.EmployeeHeadersState) {
        binding.progressBar.isVisible = false
        when {
            state.error != null -> {
                Toast.makeText(this, state.error, Toast.LENGTH_SHORT).show()
            }
            state.headers.isNotEmpty() -> {
                binding.rvHeaders.isVisible = true
                val adapter = EmployeeHeadersAdapter(state.headers)
                binding.rvHeaders.layoutManager = LinearLayoutManager(this)
                binding.rvHeaders.adapter = adapter
            }
        }
    }
}
