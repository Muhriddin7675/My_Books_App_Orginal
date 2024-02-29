package com.example.mybooksapporginal.presentantion.viewmodel

import com.example.mybooksapporginal.data.CategoryData
import kotlinx.coroutines.flow.Flow

interface LibraryPagerViewModel {
    val errorMessage: Flow<String>
    val loadData:Flow<List<CategoryData>>
    val progress:Flow<Boolean>

    fun loadLibraryData()
}