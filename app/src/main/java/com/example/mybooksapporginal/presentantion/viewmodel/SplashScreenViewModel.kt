package com.example.mybooksapporginal.presentantion.viewmodel

import kotlinx.coroutines.flow.Flow


interface SplashScreenViewModel {
    val openIntroScreen: Flow<Unit>
//    val openLoginScreen: Flow<Unit>
    val openMenuScreen: Flow<Unit>

    fun load()
}