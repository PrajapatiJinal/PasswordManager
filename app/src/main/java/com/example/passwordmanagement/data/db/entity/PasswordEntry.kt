package com.example.passwordmanagement.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PasswordEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val accountType: String,
    val username: String,
    val encryptedPassword: String
)
