package com.example.mybooksapporginal.presentantion.viewmodel

import kotlinx.coroutines.flow.Flow

interface RegisterScreenViewModel {
    val openMenuScreen:Flow<Unit>
    val errorMessage:Flow<String>
    fun createUser(name:String,email:String,password:String)
}