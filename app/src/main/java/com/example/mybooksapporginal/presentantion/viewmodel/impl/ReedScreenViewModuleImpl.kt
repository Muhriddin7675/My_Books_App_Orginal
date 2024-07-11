package com.example.mybooksapporginal.presentantion.viewmodel.impl

import androidx.lifecycle.ViewModel
import com.example.mybooksapporginal.data.local.pref.MyShared
import com.example.mybooksapporginal.presentantion.viewmodel.ReedScreenViewModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class ReedScreenViewModuleImpl @Inject constructor(
    private val myShared: MyShared

): ReedScreenViewModule,ViewModel() {
    override val setBookPath = MutableSharedFlow<Int>()

    override fun getBookPath(key: String) {
      val page =  myShared.getBookPage(key)
        setBookPath.tryEmit(page)
    }

    override fun setBookPath(key: String, value: Int) {
       myShared.setBookPage(key,value)
    }

}