package com.example.mybooksapporginal.presentantion.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybooksapporginal.data.local.pref.MyShared
import com.example.mybooksapporginal.domain.impl.AppRepositoryImpl
import com.example.mybooksapporginal.presentantion.viewmodel.SplashScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModelImpl @Inject constructor(
    private val shared: MyShared
) : ViewModel(), SplashScreenViewModel {
    override val openIntroScreen = MutableSharedFlow<Unit>()
    override val openLoginScreen = MutableSharedFlow<Unit>()
    override val openMenuScreen = MutableSharedFlow<Unit>()

    override fun load() {
        viewModelScope.launch {
            delay(1000)
            if (shared.getHasIntroPage() == 0) {
                openIntroScreen.emit(Unit)
            } else if (shared.getHasIntroPage() == 1) {
                openLoginScreen.emit(Unit)
            } else {
                openMenuScreen.emit(Unit)
            }
        }
    }
}