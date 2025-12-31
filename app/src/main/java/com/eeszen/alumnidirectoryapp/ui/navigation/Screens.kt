package com.eeszen.alumnidirectoryapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen{
    @Serializable data object Home:Screen()
    @Serializable data object RegisterForm:Screen()
    @Serializable data object Register:Screen()
    @Serializable data object Splash:Screen()
    @Serializable data object Login:Screen()
    @Serializable data object Profile:Screen()
    @Serializable data object EditProfile:Screen()
    @Serializable data object AdminDashboard:Screen()
}