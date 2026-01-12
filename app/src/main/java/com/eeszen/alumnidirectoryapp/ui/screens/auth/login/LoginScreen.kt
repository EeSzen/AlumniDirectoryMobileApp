package com.eeszen.alumnidirectoryapp.ui.screens.auth.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.eeszen.alumnidirectoryapp.ui.navigation.Screen
import com.eeszen.alumnidirectoryapp.ui.screens.auth.composables.EmailPassAuth

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.success.collect {
            navController.navigate(Screen.Home) {
                popUpTo(Screen.Login) {
                    inclusive = true
                }
            }
        }
    }

//    LaunchedEffect(Unit) {
//        viewModel.error.collect { message ->
//            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
//        }
//    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome Back",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Spacer(Modifier.height(24.dp))
            EmailPassAuth(
                modifier = Modifier.fillMaxWidth(),
                actionButtonText = "Sign In",
                buttonAction = { email, password -> viewModel.signInWithEmail(email, password) },
                googleButtonText = "Sign In with Google",
                googleButtonAction = { viewModel.signInWithGoogle(context) },
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text("Don't have an account yet?")
                    TextButton(
                        onClick = { navController.navigate(Screen.Register) }
                    ) {
                        Text("Sign Up")
                    }
                }
            }
        }
    }
}
