package com.example.mybooksapporginal.data.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mybooksapporginal.data.local.room.dao.BooksDao
import com.example.mybooksapporginal.data.local.room.entity.BookEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [BookEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getBookDao(): BooksDao
}