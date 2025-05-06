package com.example.passwordmanagement.data.repository

import androidx.lifecycle.LiveData
import com.example.passwordmanagement.data.db.dao.PasswordDao
import com.example.passwordmanagement.data.db.entity.PasswordEntry

open class PasswordRepository(private val dao: PasswordDao) {
    val allPasswords: LiveData<List<PasswordEntry>> = dao.getAllPasswords()
    suspend fun insert(entry: PasswordEntry) = dao.insertPassword(entry)
    suspend fun update(entry: PasswordEntry) = dao.updatePassword(entry)
    suspend fun delete(entry: PasswordEntry) = dao.deletePassword(entry)
}