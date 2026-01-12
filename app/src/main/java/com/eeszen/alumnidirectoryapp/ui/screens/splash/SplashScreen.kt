package com.eeszen.alumnidirectoryapp.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups3
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eeszen.alumnidirectoryapp.service.AuthService
import com.eeszen.alumnidirectoryapp.ui.navigation.Screen

@Composable
fun SplashScreen(
    navController: NavController
) {
    LaunchedEffect(Unit) {
        val nextScreen = if (AuthService.getInstance().getCurrentUser() != null) {
            Screen.Home
        } else {
            Screen.Login
        }
        navController.navigate(nextScreen) {
            popUpTo(Screen.Splash) {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(120.dp),
            imageVector = Icons.Default.Groups3,
            contentDescription = "",
            tint = Color.Black
        )
    }
}