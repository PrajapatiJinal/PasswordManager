package com.example.passwordmanagement.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.passwordmanagement.R
import com.example.passwordmanagement.data.db.entity.PasswordEntry
import com.example.passwordmanagement.ui.screens.generateRandomPassword

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordBottomSheet(
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit,
    onDelete: () -> Unit,
    passwordEntry: PasswordEntry? = null
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (passwordEntry == null) {
                    // Add New Account
                    Text(
                        text = stringResource(R.string.add_new_account),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    // Edit Account
                    Text(
                        text = stringResource(R.string.account_details),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                var accountType by remember { mutableStateOf(passwordEntry?.accountType ?: "") }
                var username by remember { mutableStateOf(passwordEntry?.username ?: "") }
                var password by remember { mutableStateOf(passwordEntry?.encryptedPassword ?: "") }

                OutlinedTextField(
                    value = accountType,
                    onValueChange = { accountType = it },
                    label = { Text(stringResource(R.string.account_name)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(stringResource(R.string.username_email)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { newPassword ->
                            if (newPassword.length <= 8) {
                                password = newPassword
                            }
                        },
                        label = { Text(stringResource(R.string.password)) },
                        modifier = Modifier.weight(1f)
                    )
                    // Generate button on the right side
                    Button(
                        onClick = {
                            password = generateRandomPassword()
                        },
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .height(42.dp)
                    ) {
                        Text(text = stringResource(R.string.generate))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (passwordEntry == null) {
                    // For adding a new account
                    Button(
                        onClick = {
                            onSave(accountType, username, password)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(R.string.add_new_account))
                    }
                } else {
                    // For editing an existing account
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                onSave(accountType, username, password)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        ) {
                            Text(text = stringResource(R.string.edit))
                        }
                        Button(
                            onClick = {
                                onDelete()
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = stringResource(R.string.delete))
                        }
                    }
                }
            }
        }
    )
}