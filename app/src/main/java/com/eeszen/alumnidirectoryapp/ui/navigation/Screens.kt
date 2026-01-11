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
    @Serializable data class EditProfile(val id: String):Screen()
    @Serializable object AdminDashboard:Screen()
    @Serializable object PendingRegistrations:Screen()
    @Serializable object ApprovedAlumni:Screen()
    @Serializable object RejectedAlumni:Screen()
    @Serializable object ManageUsers:Screen()
    @Serializable object RecentApprovals:Screen()
}