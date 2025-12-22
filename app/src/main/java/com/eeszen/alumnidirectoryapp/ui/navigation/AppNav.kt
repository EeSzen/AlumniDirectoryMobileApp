package com.eeszen.alumnidirectoryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eeszen.alumnidirectoryapp.ui.screens.home.HomeScreen

@Composable
fun AppNav(){
    val navController = rememberNavController()
    NavHost(
        startDestination = Screen.Home,
        navController = navController
    ) {
        composable<Screen.Home> {
            HomeScreen()
        }
    }
}