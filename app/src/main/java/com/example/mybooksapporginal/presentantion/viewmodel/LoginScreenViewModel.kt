package com.example.mybooksapporginal.presentantion.viewmodel

import androidx.dynamicanimation.animation.FlingAnimation
import kotlinx.coroutines.flow.Flow

interface LoginScreenViewModel {
    val openMenuScreen : Flow<Unit>
    val errorMessage:Flow<String>

    fun loginEmailInPassword(emil:String,password:String)
}