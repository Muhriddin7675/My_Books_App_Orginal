package com.example.mybooksapporginal.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CategoryData(
    val categoryDocId: String,
    val bookList: ArrayList<BookData>
) : Parcelable