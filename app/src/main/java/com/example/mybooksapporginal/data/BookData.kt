package com.example.mybooksapporginal.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class BookData(
    val docId: String,
    val bookAuthor: String,
    val bookDescription: String,
    val bookImage: String,
    val bookName: String,
    val bookPath: String,
    val category: String,
    val categoryName: String,
    val size: Int
):Parcelable