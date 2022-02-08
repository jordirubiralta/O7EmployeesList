package com.example.o7employeeslist.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.o7employeeslist.ui.analytics.AnalyticsFragment
import com.example.o7employeeslist.ui.list.EmployeeListFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> EmployeeListFragment.newInstance()
            1 -> AnalyticsFragment.newInstance()
            else -> EmployeeListFragment.newInstance()
        }

}
