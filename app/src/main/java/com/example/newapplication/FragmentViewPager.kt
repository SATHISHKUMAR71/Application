package com.example.newapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentViewPager(private val fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    private val fragmentList = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment){
        fragmentList.add(fragment)
    }
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}