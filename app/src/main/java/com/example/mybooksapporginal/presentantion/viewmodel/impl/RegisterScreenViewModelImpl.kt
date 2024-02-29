package com.example.mybooksapporginal.presentantion.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybooksapporginal.data.LoginUserFirebaseData
import com.example.mybooksapporginal.domain.LoginRepository
import com.example.mybooksapporginal.presentantion.viewmodel.RegisterScreenViewModel
import com.example.mybooksapporginal.util.myLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModelImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel(), RegisterScreenViewModel {
    override val openMenuScreen =
        MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val errorMessage =
        MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override fun createUser(name: String, email: String, password: String) {
        loginRepository.createUserEmailInPassword(email, password)
            .onEach { result ->
                result.onSuccess { uid ->
                    loginRepository.addUserFirebase(
                        LoginUserFirebaseData(
                            name = name,
                            uid = uid,
                            email = email,
                            password = password
                        )
                    ).onEach {}.launchIn(viewModelScope)

                    uid.myLog()
                    name.myLog()
                    "viewModule".myLog()
                }
                result.onFailure {
                    errorMessage.tryEmit(it.message.toString())
                }
            }
            .launchIn(viewModelScope)

    }
}