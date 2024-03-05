package com.example.mybooksapporginal.presentantion.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.presentantion.viewmodel.SplashScreenViewModel
import com.example.mybooksapporginal.presentantion.viewmodel.impl.SplashScreenViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {

    private val viewModel: SplashScreenViewModel by viewModels<SplashScreenViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.load()

        viewModel.openIntroScreen
            .onEach { navController.navigate(SplashScreenDirections.actionSplashScreenToIntroScreen()) }
            .launchIn(lifecycleScope)

        viewModel.openMenuScreen
            .onEach { navController.navigate(SplashScreenDirections.actionSplashScreenToMenuScreen()) }
            .launchIn(lifecycleScope)
    }
}