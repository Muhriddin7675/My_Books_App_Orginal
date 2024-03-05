package com.example.mybooksapporginal.presentantion.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybooksapporginal.data.CategoryData
import com.example.mybooksapporginal.domain.AppRepository
import com.example.mybooksapporginal.presentantion.viewmodel.LibraryPagerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LibraryPagerViewModelImpl @Inject constructor(
    private val repositoryImpl: AppRepository
) : ViewModel(), LibraryPagerViewModel {
    override val errorMessage = MutableSharedFlow<String>()
    override val loadData = MutableSharedFlow<List<CategoryData>>()
    override val progress = MutableStateFlow(false)

    override fun loadLibraryData() {
        progress.value = true
        repositoryImpl.requestAllBook()
            .onEach { result ->
                progress.value = false
                result.onSuccess {
                    loadData.emit(it)
                }
                result.onFailure {
                    errorMessage.emit(it.message.toString())
                }
            }
            .launchIn(viewModelScope)
    }
}