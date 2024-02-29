package com.example.mybooksapporginal.presentantion.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mybooksapporginal.presentantion.screen.page.LibraryPager
import com.example.mybooksapporginal.presentantion.screen.page.MyBooksPager
import com.example.mybooksapporginal.presentantion.screen.page.MyFavoritePager

class MenuScreenAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> LibraryPager()
        1 -> MyBooksPager()
        else -> MyFavoritePager()
    }
}