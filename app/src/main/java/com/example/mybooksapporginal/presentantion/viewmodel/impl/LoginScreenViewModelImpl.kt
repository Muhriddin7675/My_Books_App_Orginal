package com.example.mybooksapporginal.presentantion.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybooksapporginal.domain.LoginRepository
import com.example.mybooksapporginal.domain.impl.LoginRepositoryImpl
import com.example.mybooksapporginal.presentantion.viewmodel.LoginScreenViewModel
import com.example.mybooksapporginal.util.myLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModelImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel(), LoginScreenViewModel {
    override val openMenuScreen =  MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val errorMessage = MutableSharedFlow<String> (replay =1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override fun loginEmailInPassword(email: String, password: String) {

        loginRepository.loginEmailInPassword(email,password)
             .onEach {
                 if(it){
                     openMenuScreen.emit(Unit)
                 }
                 else{
                     errorMessage.emit("Siz oldin ro'yxatdan o'tmagansiz !")
                 }
             }
             .launchIn(viewModelScope)
    }
}