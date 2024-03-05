package com.example.mybooksapporginal.presentantion.viewmodel

import com.example.mybooksapporginal.data.BookData
import kotlinx.coroutines.flow.Flow
import java.io.File

interface BookInfoViewModule {
    val isHasBookListener : Flow<Boolean>
    val fileLiveData : Flow<File>
    val errorMessage : Flow<String>
    fun hasBookFromLocal(docID :String)
    fun getDownloadedBook(data: BookData)
}