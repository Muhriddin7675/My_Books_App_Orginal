package com.example.mybooksapporginal.domain

import com.example.mybooksapporginal.data.BookData
import com.example.mybooksapporginal.data.CategoryData
import com.example.mybooksapporginal.data.enamdata.UploadBookData
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AppRepository {
    fun getAllLibraryBooks(): Flow<Result<List<CategoryData>>>

    fun requestAllBook() : Flow<Result<List<CategoryData>>>

    fun hasBookFromLocal(docId: String): Flow<Result<Boolean>>
    fun downloadBookPdf(data: BookData): Flow<UploadBookData>
    fun getAllLocalBook(): Flow<List<BookData>>
    fun getDownloadedBook(data: BookData): Flow<File>

    fun pauseBookUploading()
    fun cancelBookUploading()
    fun resumeBookUploading()

}