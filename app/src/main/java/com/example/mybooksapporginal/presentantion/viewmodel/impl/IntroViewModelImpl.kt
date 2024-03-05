package com.example.mybooksapporginal.presentantion.viewmodel.impl

import androidx.lifecycle.ViewModel
import com.example.mybooksapporginal.data.local.pref.MyShared
import com.example.mybooksapporginal.presentantion.viewmodel.IntroViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModelImpl @Inject constructor(
    private val myShared: MyShared
) : ViewModel(), IntroViewModel {
    override fun setMyShared() {
        myShared.setHasIntroPage(1)
    }

}