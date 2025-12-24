package com.eeszen.alumnidirectoryapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen{
    @Serializable data object Home:Screen()
    @Serializable data object Register:Screen()
}