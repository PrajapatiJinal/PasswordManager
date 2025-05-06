package com.example.passwordmanagement.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanagement.data.db.entity.PasswordEntry
import com.example.passwordmanagement.data.repository.PasswordRepository
import com.example.passwordmanagement.utils.EncryptionUtils
import kotlinx.coroutines.launch

class PasswordViewModel(private val repository: PasswordRepository) : ViewModel() {
    val allPasswords: LiveData<List<PasswordEntry>> = repository.allPasswords

    fun addPassword(accountType: String, username: String, password: String) =
        viewModelScope.launch {
            val encrypted = EncryptionUtils.encrypt(password)
            repository.insert(
                PasswordEntry(
                    accountType = accountType,
                    username = username,
                    encryptedPassword = encrypted
                )
            )
        }

    fun updatePassword(
        entry: PasswordEntry,
        newAccountType: String,
        newUsername: String,
        newPassword: String
    ) = viewModelScope.launch {
        val encrypted = EncryptionUtils.encrypt(newPassword)
        repository.update(
            entry.copy(
                accountType = newAccountType,
                username = newUsername,
                encryptedPassword = encrypted
            )
        )
    }

    fun deletePassword(entry: PasswordEntry) = viewModelScope.launch {
        repository.delete(entry)
    }

    fun getDecryptedPassword(entry: PasswordEntry): String {
        return EncryptionUtils.decrypt(entry.encryptedPassword)
    }
}