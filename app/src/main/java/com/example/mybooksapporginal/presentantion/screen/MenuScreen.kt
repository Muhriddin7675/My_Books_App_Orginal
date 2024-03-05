package com.example.mybooksapporginal.presentantion.screen

import android.media.MediaSession2Service.MediaNotification
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.databinding.MenuScreenBinding
import com.example.mybooksapporginal.presentantion.adapter.MenuScreenAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuScreen : Fragment(R.layout.menu_screen) {
    private val binding by viewBinding(MenuScreenBinding::bind)
    private lateinit var adapter: MenuScreenAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MenuScreenAdapter(childFragmentManager, lifecycle)

        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottomLibrary -> binding.viewPager.currentItem = 0
                R.id.bottomMyBooks -> binding.viewPager.currentItem = 1
            }
            return@setOnItemSelectedListener true
        }
    }
}