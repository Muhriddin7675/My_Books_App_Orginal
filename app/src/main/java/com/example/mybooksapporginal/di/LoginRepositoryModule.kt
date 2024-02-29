package com.example.mybooksapporginal.di

import com.example.mybooksapporginal.domain.LoginRepository
import com.example.mybooksapporginal.domain.impl.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LoginRepositoryModule {
    @Binds
    fun getLogin(impl: LoginRepositoryImpl):LoginRepository

}