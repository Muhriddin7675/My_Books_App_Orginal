package com.example.mybooksapporginal.presentantion.viewmodel.impl

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mybooksapporginal.domain.AppRepository
import com.example.mybooksapporginal.domain.impl.AppRepositoryImpl
import com.example.mybooksapporginal.presentantion.viewmodel.MyBooksPagerViewModel
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class MyBooksPagerViewModelImpl @Inject constructor(
    private val repository: AppRepository
):ViewModel(), MyBooksPagerViewModel {
}