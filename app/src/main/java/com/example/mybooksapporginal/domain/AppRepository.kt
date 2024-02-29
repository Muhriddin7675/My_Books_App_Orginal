package com.example.mybooksapporginal.domain

import com.example.mybooksapporginal.data.CategoryData
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getAllLibraryBooks(): Flow<Result<List<CategoryData>>>

}