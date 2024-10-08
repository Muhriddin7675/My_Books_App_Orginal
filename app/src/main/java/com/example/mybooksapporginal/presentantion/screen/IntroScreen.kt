package com.example.mybooksapporginal.presentantion.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.data.local.pref.MyShared
import com.example.mybooksapporginal.databinding.ScreenIntroBinding
import com.example.mybooksapporginal.presentantion.adapter.IntroScreenAdapter
import com.example.mybooksapporginal.presentantion.viewmodel.IntroViewModel
import com.example.mybooksapporginal.presentantion.viewmodel.impl.IntroViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroScreen : Fragment(R.layout.screen_intro) {
    private val binding by viewBinding(ScreenIntroBinding::bind)
    private lateinit var adapter: IntroScreenAdapter
    private val viewModel: IntroViewModel by viewModels<IntroViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = IntroScreenAdapter(childFragmentManager, lifecycle)
        binding.pager.adapter = adapter
        binding.springDotsIndicator.attachTo(binding.pager)

        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        binding.btnNext.text = "Keyingisi"
                    }

                    1 -> {
                        binding.btnNext.text = "Keyingisi"
                    }

                    else -> {
                        binding.btnNext.text = "Davom etish"
                    }
                }
            }
        })
        binding.btnNext.setOnClickListener {
            if (binding.pager.currentItem < 2) binding.pager.currentItem++
            else {
                viewModel.setMyShared()
                navController.navigate(IntroScreenDirections.actionIntroScreenToMenuScreen())
            }
        }
    }
}