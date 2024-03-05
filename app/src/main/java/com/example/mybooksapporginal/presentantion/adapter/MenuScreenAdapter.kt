package com.example.mybooksapporginal.presentantion.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mybooksapporginal.presentantion.screen.page.LibraryPager
import com.example.mybooksapporginal.presentantion.screen.page.MyBooksPager

class MenuScreenAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> LibraryPager()
        else -> MyBooksPager()
    }
}