package com.eeszen.alumnidirectoryapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eeszen.alumnidirectoryapp.ui.components.navbar.BottomNavigationBar
import com.eeszen.alumnidirectoryapp.ui.navigation.AppNav
import com.eeszen.alumnidirectoryapp.ui.navigation.Screen
import com.eeszen.alumnidirectoryapp.ui.theme.MOBStarterAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MOBStarterAppTheme {
                ComposeApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ComposeApp(){
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val showBottomBar =
        currentDestination
            ?.route
            ?.contains(Screen.Login::class.qualifiedName ?: "") != true &&
                currentDestination
                    ?.route
                    ?.contains(Screen.Register::class.qualifiedName ?: "") != true &&
                currentDestination
                    ?.route
                    ?.contains(Screen.Splash::class.qualifiedName ?: "") != true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) {
        AppNav(navController)
    }
}