package com.example.mybooksapporginal.domain

import com.example.mybooksapporginal.data.LoginUserFirebaseData
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun loginEmailInPassword(email :String,password:String): Flow<Boolean>
    fun createUserEmailInPassword(email: String,password: String):Flow<Result<String>>
    fun addUserFirebase(data:LoginUserFirebaseData):Flow<Unit>
}