package com.example.mybooksapporginal.presentantion.screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.databinding.ScreenLoginBinding
import com.example.mybooksapporginal.presentantion.viewmodel.LoginScreenViewModel
import com.example.mybooksapporginal.presentantion.viewmodel.impl.LoginScreenViewModelImpl
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges

@AndroidEntryPoint
class LoginScreen :Fragment(R.layout.screen_login) {
    private val binding by viewBinding(ScreenLoginBinding::bind)
    private val viewModel :LoginScreenViewModel by viewModels<LoginScreenViewModelImpl>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(LoginScreenDirections.actionLoginScreenToRegisterScreen())
        }
        combine(
            binding.editEmail.textChanges().map { it.endsWith("@gmail.com") },
            binding.editPassword.textChanges().map { it.length >= 6 },
            transform = {  email, password ->  email && password }
        ).onEach {
            binding.btnLogin.isEnabled = it
        }.launchIn(lifecycleScope)

        binding.btnLogin.setOnClickListener {
            viewModel.loginEmailInPassword(binding.editEmail.text.toString(),binding.editPassword.text.toString())
        }

        viewModel.openMenuScreen
            .onEach { findNavController().navigate(LoginScreenDirections.actionLoginScreenToMenuScreen()) }
            .launchIn(lifecycleScope)

        viewModel.errorMessage
            .onEach { Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show() }
            .launchIn(lifecycleScope)
    }

}