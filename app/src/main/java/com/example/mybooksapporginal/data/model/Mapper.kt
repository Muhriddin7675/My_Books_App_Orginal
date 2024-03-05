package com.example.mybooksapporginal.data.model

import com.example.mybooksapporginal.data.BookData
import com.example.mybooksapporginal.data.local.room.entity.BookEntity

object Mapper {
    fun BookEntity.toBookData(): BookData =
        BookData(
            docId = this.docId,
            bookAuthor = this.bookAuthor,
            bookName = this.bookName,
            bookPath = this.bookPath,
            bookDescription = this.description,
            bookImage = this.imagePath,
            size = this.bookSize,
            category = this.category,
            categoryName = this.categoryName
        )
}