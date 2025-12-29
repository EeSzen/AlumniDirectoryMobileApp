package com.eeszen.alumnidirectoryapp.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eeszen.alumnidirectoryapp.service.AuthService
import com.eeszen.alumnidirectoryapp.ui.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val scope = CoroutineScope(Dispatchers.IO)
    val context = LocalContext.current
    val authService = AuthService.getInstance()

    fun signIn(){
        scope.launch {
            authService.signInWithGoogle(context)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Login")

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (error != null) {
            Text(error!!)
        }

        Button(
            onClick = {
                scope.launch {
                    try {
                        val user = AuthService.getInstance().signInWithEmail(email.trim(), password)
                        if (user != null) {
                            navController.navigate(Screen.Splash) {
                                popUpTo(Screen.Login) { inclusive = true }
                            }
                        } else {
                            error = "Login failed"
                        }
                    } catch (e: Exception) {
                        error = e.message ?: "Login failed"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Button(
            onClick = { navController.navigate(Screen.Register) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Register")
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                signIn()
            }
        ) {
            Text("Sign In with google")
        }
    }
}

// the supplied auth credential is incorrect, malformed or has expired