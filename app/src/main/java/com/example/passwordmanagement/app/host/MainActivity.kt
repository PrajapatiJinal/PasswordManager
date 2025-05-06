package com.example.passwordmanagement.app.host

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.passwordmanagement.ui.screens.PasswordManagerApp
import com.example.passwordmanagement.ui.theme.PasswordManagementTheme
import android.content.Context
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Show biometric prompt on app launch
        showBiometricPrompt { isAuthenticated ->
            if (isAuthenticated) {
                // Proceed with the app flow
                setContent {
                    PasswordManagementTheme {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            PasswordManagerApp(innerPadding)
                        }
                    }
                }
            } else {
                // Handle authentication failure (e.g., show a message or close the app)
                finish() // Close the app if authentication fails
            }
        }
    }

    private fun showBiometricPrompt(onResult: (Boolean) -> Unit) {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricManager = BiometricManager.from(this)

        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Authenticate")
                    .setSubtitle("Use your biometric to access the app")
                    .setNegativeButtonText("Cancel")
                    .build()

                val biometricPrompt = BiometricPrompt(this, executor,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            onResult(true)
                        }

                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                            super.onAuthenticationError(errorCode, errString)
                            if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                                finish()
                            }
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                        }
                    })

                biometricPrompt.authenticate(promptInfo)
            }
            else -> {
                onResult(true)
            }
        }
    }
}


