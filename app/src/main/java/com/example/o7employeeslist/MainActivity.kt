package com.example.o7employeeslist

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.o7employeeslist.databinding.MainActivityBinding
import com.example.o7employeeslist.ui.ViewPagerAdapter
import com.example.o7employeeslist.ui.addemployee.NewEmployeeActivity
import com.example.o7employeeslist.ui.list.EmployeeListFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            (supportFragmentManager.fragments[0] as? EmployeeListFragment)?.updateData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        val adapter = ViewPagerAdapter(this)
        binding.container.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.container) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.employees)
                1 -> tab.text = getString(R.string.analytics)
            }
        }.attach()

        binding.floatingButton.setOnClickListener {
            val intent = Intent(this, NewEmployeeActivity::class.java)
            responseLauncher.launch(intent)
        }
    }
}
