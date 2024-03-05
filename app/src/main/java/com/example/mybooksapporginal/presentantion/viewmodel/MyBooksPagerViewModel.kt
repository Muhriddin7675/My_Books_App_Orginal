package com.example.mybooksapporginal.presentantion.viewmodel

import com.example.mybooksapporginal.data.BookData
import kotlinx.coroutines.flow.Flow

interface MyBooksPagerViewModel {
    val loadLocalBooks :Flow<List<BookData>>
    val progress :Flow<Boolean>
    fun getLocalBooksList()
}