package com.example.mybooksapporginal.presentantion.viewmodel

import kotlinx.coroutines.flow.Flow

interface ReedScreenViewModule{
    val setBookPath: Flow<Int>
    fun getBookPath(key:String)
    fun setBookPath(key:String,value:Int)
}