package com.example.passwordmanagement.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanagement.R
import com.example.passwordmanagement.data.db.entity.PasswordEntry

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    passwords: List<PasswordEntry>,
    onAddClick: () -> Unit,
    onPasswordClick: (PasswordEntry) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F6FA))
                .padding(16.dp)
        ) {
            Text(
                stringResource(R.string.password_manager),
                Modifier.padding(top = innerPadding.calculateTopPadding()),
                style = MaterialTheme.typography.labelLarge,
                fontSize = 22.sp
            )
            Spacer(Modifier.height(16.dp))

            LazyColumn {
                items(passwords.size) { idx ->
                    val entry = passwords[idx]
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onPasswordClick(entry) },
                        shape = RoundedCornerShape(40.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(0.5f)
                            ) {
                                Text(
                                    entry.accountType,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.Black
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("********", style = MaterialTheme.typography.bodyMedium)
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = "Navigate",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val context = LocalContext.current
    val passwords = remember {
        mutableListOf(
            PasswordEntry(
                1, "gmail", "********",
                encryptedPassword = "jj"
            ),
            PasswordEntry(2, "github", "********", "fkf")
        )
    }
    val onAddClick = {
    }
    val onPasswordClick: (PasswordEntry) -> Unit = {
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F6FA))
                .padding(16.dp)
        ) {
            HomeScreen(it, passwords, onAddClick = {}, onPasswordClick = {})
        }
    }

}