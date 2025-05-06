package com.example.passwordmanagement.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.passwordmanagement.data.db.dao.PasswordDao
import com.example.passwordmanagement.data.db.entity.PasswordEntry

@Database(entities = [PasswordEntry::class], version = 1)
abstract class PasswordDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao
}