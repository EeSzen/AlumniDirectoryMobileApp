package com.eeszen.alumnidirectoryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eeszen.alumnidirectoryapp.ui.screens.admin.AdminDashboardScreen
import com.eeszen.alumnidirectoryapp.ui.screens.home.HomeScreen
import com.eeszen.alumnidirectoryapp.ui.screens.profile.EditProfileScreen
import com.eeszen.alumnidirectoryapp.ui.screens.profile.ProfileScreen
import com.eeszen.alumnidirectoryapp.ui.screens.registration.RegisterScreen
import com.eeszen.alumnidirectoryapp.ui.screens.splash.SplashScreen

@Composable
fun AppNav(){
    val navController = rememberNavController()
    NavHost(
        startDestination = Screen.AdminDashboard,
        navController = navController
    ) {
        composable<Screen.Home> {
            HomeScreen(navController)
        }
        composable<Screen.Register> {
            RegisterScreen()
        composable<Screen.Splash> {
            SplashScreen(navController)
        }
        composable<Screen.Profile> {
            ProfileScreen(navController)
        }
        composable<Screen.EditProfile> {
            EditProfileScreen(navController)
        }
        composable<Screen.AdminDashboard> {
            AdminDashboardScreen(navController)
        }
    }
}