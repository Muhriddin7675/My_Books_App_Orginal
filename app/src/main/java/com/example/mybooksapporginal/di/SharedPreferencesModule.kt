package com.example.mybooksapporginal.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mybooksapporginal.data.local.room.AppDatabase
import com.example.mybooksapporginal.data.local.room.dao.BooksDao
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

    @[Provides Singleton]
    fun provideBookDao(database: AppDatabase): BooksDao = database.getBookDao()

    @[Provides Singleton]
    fun providesBookDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "MyBooks.dp")
            .allowMainThreadQueries()
            .build()
}