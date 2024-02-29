package com.example.mybooksapporginal.presentantion.viewmodel.impl

import androidx.lifecycle.ViewModel
import com.example.mybooksapporginal.presentantion.viewmodel.BookInfoViewModule
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookInfoViewModuleImpl @Inject constructor() : BookInfoViewModule, ViewModel() {

}