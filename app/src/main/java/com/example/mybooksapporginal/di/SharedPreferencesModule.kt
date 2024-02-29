package com.example.mybooksapporginal.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {
    @[Provides Singleton]
    fun providePref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("BookApp", Context.MODE_PRIVATE)
}