package com.example.passwordmanagement.app.host

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.passwordmanagement.ui.screens.PasswordManagerApp
import com.example.passwordmanagement.ui.theme.PasswordManagementTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PasswordManagementTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PasswordManagementTheme {
                        PasswordManagerApp(innerPadding)
                    }
                }
            }
        }
    }
}


