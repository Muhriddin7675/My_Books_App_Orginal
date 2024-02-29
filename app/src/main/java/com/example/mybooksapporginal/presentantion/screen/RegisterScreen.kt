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
import com.example.mybooksapporginal.databinding.ScreenRegisterBinding
import com.example.mybooksapporginal.presentantion.viewmodel.RegisterScreenViewModel
import com.example.mybooksapporginal.presentantion.viewmodel.impl.RegisterScreenViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges

@AndroidEntryPoint
class RegisterScreen : Fragment(R.layout.screen_register) {

    private val binding by viewBinding(ScreenRegisterBinding::bind)
    private val viewModel: RegisterScreenViewModel by viewModels<RegisterScreenViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        combine(
            binding.editFirstName.textChanges().map { it.length > 3 },
            binding.editLastName.textChanges().map { it.length > 3 },
            binding.editEmail.textChanges().map { it.endsWith("@gmail.com") },
            binding.editPassword.textChanges().map { it.length >= 6 },
            transform = { firstName, lastName, email, password -> firstName && lastName && email && password }
        ).onEach {
            binding.next.isEnabled = it
        }.launchIn(lifecycleScope)

        binding.next.setOnClickListener {
            viewModel.createUser(
                name = "${binding.editFirstName.text} ${binding.editLastName.text}",
                email = binding.editEmail.text.toString(),
                password = binding.editPassword.text.toString()
            )
        }
        viewModel.errorMessage
            .onEach { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
            .launchIn(lifecycleScope)

        viewModel.openMenuScreen
            .onEach { findNavController().navigate(RegisterScreenDirections.actionRegisterScreenToMenuScreen()) }
            .launchIn(lifecycleScope)

    }
}