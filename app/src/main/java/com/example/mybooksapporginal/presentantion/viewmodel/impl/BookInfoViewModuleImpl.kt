package com.example.mybooksapporginal.presentantion.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybooksapporginal.data.BookData
import com.example.mybooksapporginal.domain.AppRepository
import com.example.mybooksapporginal.presentantion.viewmodel.BookInfoViewModule
import com.example.mybooksapporginal.util.myLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

@HiltViewModel
class BookInfoViewModuleImpl @Inject constructor(
    private val repository: AppRepository
) : BookInfoViewModule, ViewModel() {
    override val isHasBookListener = MutableSharedFlow<Boolean>()
    override val fileLiveData = MutableSharedFlow<File>()
    override val errorMessage = MutableSharedFlow<String>()

    override fun hasBookFromLocal(docID: String) {
        repository.hasBookFromLocal(docID)
            .onEach { result ->
                result.onSuccess {
                    isHasBookListener.emit(it)
                }
                result.onFailure {
                    errorMessage.emit(it.message.toString())
                }
            }
            .launchIn(viewModelScope)
    }

    override fun getDownloadedBook(data: BookData) {
        repository.getDownloadedBook(data)
            .onEach {
                "Path : "+it.path.toString().myLog()
                fileLiveData.emit(it)
            }.launchIn(viewModelScope)
    }

}