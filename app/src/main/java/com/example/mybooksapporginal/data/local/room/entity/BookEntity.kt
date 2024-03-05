package com.example.mybooksapporginal.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookTable")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    val docId: String,
    val bookAuthor: String,
    val bookName: String,
    val description: String,
    val imagePath: String,
    val bookPath: String,
    val bookSize: Int,
    val category: String,
    val categoryName: String,
)