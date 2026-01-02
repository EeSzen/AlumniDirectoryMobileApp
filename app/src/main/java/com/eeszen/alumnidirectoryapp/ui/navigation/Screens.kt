package com.eeszen.alumnidirectoryapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen{
    @Serializable object Home:Screen()
    @Serializable object RegisterForm:Screen()
    @Serializable object Register:Screen()
    @Serializable object Splash:Screen()
    @Serializable object Login:Screen()
    @Serializable data class Profile(val id: String):Screen()
    @Serializable object EditProfile:Screen()
    @Serializable object AdminDashboard:Screen()
}