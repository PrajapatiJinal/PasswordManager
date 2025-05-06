package com.example.passwordmanagement.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.example.passwordmanagement.viewModel.PasswordViewModel
import org.koin.androidx.compose.koinViewModel
import com.example.passwordmanagement.data.db.entity.PasswordEntry
import com.example.passwordmanagement.ui.components.PasswordBottomSheet
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.passwordmanagement.R

@Composable
fun PasswordManagerApp(
    innerPadding: PaddingValues, viewModel: PasswordViewModel = koinViewModel()
) {

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedPassword by remember { mutableStateOf<PasswordEntry?>(null) }

    val passwords by viewModel.allPasswords.observeAsState(emptyList())

    HomeScreen(
        innerPadding,
        passwords = passwords,
        onAddClick = {
            selectedPassword = null
            showBottomSheet = true
        },
        onPasswordClick = {
            selectedPassword = it
            showBottomSheet = true
        }
    )

    if (showBottomSheet) {
        val context = LocalContext.current // Get the current context

        PasswordBottomSheet(
            onDismiss = { showBottomSheet = false },
            onSave = { accountType, username, password ->
                when {
                    accountType.isBlank() -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.account_type_is_mandatory),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    username.isBlank() -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.username_is_mandatory), Toast.LENGTH_SHORT
                        ).show()
                    }

                    password.isBlank() -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.password_is_mandatory), Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        if (selectedPassword == null) {
                            viewModel.addPassword(accountType, username, password)
                        } else {
                            viewModel.updatePassword(
                                selectedPassword!!,
                                accountType,
                                username,
                                password
                            )
                        }
                        // Dismiss the bottom sheet only if save is successful
                        showBottomSheet = false
                    }
                }
            },
            passwordEntry = selectedPassword?.let {
                it.copy(encryptedPassword = viewModel.getDecryptedPassword(it))
            },
            onDelete = {
                if (selectedPassword != null) {
                    viewModel.deletePassword(selectedPassword!!)
                    selectedPassword = null
                }
            }
        )
    }
}

// Function to generate a random password
fun generateRandomPassword(length: Int = 8): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+"
    return (1..length)
        .map { chars.random() }
        .joinToString("")
}