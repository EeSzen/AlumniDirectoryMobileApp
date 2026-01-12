package com.eeszen.alumnidirectoryapp.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eeszen.alumnidirectoryapp.core.utils.SnackbarController
import com.eeszen.alumnidirectoryapp.ui.screens.admin.AdminDashboardScreen
import com.eeszen.alumnidirectoryapp.ui.screens.admin.approved.ApprovedAlumniScreen
import com.eeszen.alumnidirectoryapp.ui.screens.admin.manageUsers.ManageUsersScreen
import com.eeszen.alumnidirectoryapp.ui.screens.admin.pending.PendingRegistrationsScreen
import com.eeszen.alumnidirectoryapp.ui.screens.admin.rejected.RejectedAlumniScreen
import com.eeszen.alumnidirectoryapp.ui.screens.home.HomeScreen
import com.eeszen.alumnidirectoryapp.ui.screens.auth.login.LoginScreen
import com.eeszen.alumnidirectoryapp.ui.screens.auth.register.RegisterFormScreen
import com.eeszen.alumnidirectoryapp.ui.screens.auth.register.RegisterScreen
import com.eeszen.alumnidirectoryapp.ui.screens.profile.EditProfileScreen
import com.eeszen.alumnidirectoryapp.ui.screens.profile.ProfileScreen
import com.eeszen.alumnidirectoryapp.ui.screens.splash.SplashScreen

@Composable
fun AppNav(
    navController: NavHostController,
    onTitleChange: (String) -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        SnackbarController.events.collect {
            Log.d("debugging","hwllo")
//            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = it.msg,
                duration = SnackbarDuration.Short
            )
        }
    }

    Box {
        NavHost(
            startDestination = Screen.Login,
            navController = navController
        ) {
            composable<Screen.Home> {
                onTitleChange("Home")
                HomeScreen(navController)
            }
            composable<Screen.Register> {
                onTitleChange("Register")
                RegisterScreen(navController)
            }
            composable<Screen.RegisterForm> {
                onTitleChange("Register Form")
                RegisterFormScreen(navController)
            }
            composable<Screen.Splash> {
                SplashScreen(navController)
            }
            composable<Screen.Login> {
                onTitleChange("Login")
                LoginScreen(navController)
            }
            composable<Screen.AdminDashboard> {
                onTitleChange("Admin Dashboard")
                AdminDashboardScreen(navController)
            }
            composable<Screen.EditProfile> {
                onTitleChange("Edit Profile")
                EditProfileScreen(navController)
            }
            composable<Screen.Profile> {
                onTitleChange("Profile")
                ProfileScreen(navController)
            }
            composable<Screen.PendingRegistrations> {
                onTitleChange("Pending Registrations")
                PendingRegistrationsScreen(navController)
            }
            composable<Screen.ApprovedAlumni> {
                onTitleChange("Approved Alumni")
                ApprovedAlumniScreen(navController)
            }
            composable<Screen.RejectedAlumni> {
                onTitleChange("Rejected Alumni")
                RejectedAlumniScreen(navController)
            }
            composable<Screen.ManageUsers> {
                onTitleChange("Manage Users")
                ManageUsersScreen(navController)
            }
        }

        SnackbarHost(
            snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}