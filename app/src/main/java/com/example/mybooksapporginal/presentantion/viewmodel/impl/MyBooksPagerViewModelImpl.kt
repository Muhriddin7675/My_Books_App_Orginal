package com.example.mybooksapporginal.presentantion.viewmodel.impl

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybooksapporginal.data.BookData
import com.example.mybooksapporginal.domain.AppRepository
import com.example.mybooksapporginal.domain.impl.AppRepositoryImpl
import com.example.mybooksapporginal.presentantion.viewmodel.MyBooksPagerViewModel
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class MyBooksPagerViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : ViewModel(), MyBooksPagerViewModel {
    override val loadLocalBooks = MutableSharedFlow<List<BookData>>()
    override val progress = MutableSharedFlow<Boolean>()

    override fun getLocalBooksList() {
        progress.tryEmit(true)
        repository.getAllLocalBook()
            .onEach { ls ->
                progress.emit(false)
                loadLocalBooks.emit(ls)
            }
            .launchIn(viewModelScope)
    }
}