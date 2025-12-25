package com.eeszen.alumnidirectoryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eeszen.alumnidirectoryapp.ui.screens.home.HomeScreen
import com.eeszen.alumnidirectoryapp.ui.screens.profile.ProfileScreen
import com.eeszen.alumnidirectoryapp.ui.screens.splash.SplashScreen

@Composable
fun AppNav(){
    val navController = rememberNavController()
    NavHost(
        startDestination = Screen.Profile,
        navController = navController
    ) {
        composable<Screen.Home> {
            HomeScreen()
        }
        composable<Screen.Splash> {
            SplashScreen(navController)
        }
        composable<Screen.Profile> {
            ProfileScreen(navController)
        }
    }
}