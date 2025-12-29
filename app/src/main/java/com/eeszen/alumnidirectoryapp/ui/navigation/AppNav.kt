package com.eeszen.alumnidirectoryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eeszen.alumnidirectoryapp.ui.screens.home.HomeScreen
import com.eeszen.alumnidirectoryapp.ui.screens.auth.LoginScreen
import com.eeszen.alumnidirectoryapp.ui.screens.auth.RegisterScreen
import com.eeszen.alumnidirectoryapp.ui.screens.splash.SplashScreen

@Composable
fun AppNav(){
    val navController = rememberNavController()
    NavHost(
        startDestination = Screen.Login,
        navController = navController
    ) {
        composable<Screen.Home> {
            HomeScreen()
        }
        composable<Screen.Register> {
            RegisterScreen()
        }
        composable<Screen.Splash> {
            SplashScreen(navController)
        }
        composable<Screen.Login> {
            LoginScreen(navController)
        }
    }
}