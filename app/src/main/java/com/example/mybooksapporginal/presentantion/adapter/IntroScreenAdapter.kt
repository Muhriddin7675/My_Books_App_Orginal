package com.example.mybooksapporginal.presentantion.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mybooksapporginal.presentantion.screen.page.IntroPager

class IntroScreenAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = IntroPager().apply {
        arguments = bundleOf(Pair("Pos", position))
    }
}