package com.example.mybooksapporginal.presentantion.viewmodel.impl

import androidx.lifecycle.ViewModel
import com.example.mybooksapporginal.domain.AppRepository
import com.example.mybooksapporginal.presentantion.viewmodel.MyFavoritePagerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class MyFavoritePagerViewModelImpl @Inject constructor(
    private val repository: AppRepository
):ViewModel(),MyFavoritePagerViewModel{

}