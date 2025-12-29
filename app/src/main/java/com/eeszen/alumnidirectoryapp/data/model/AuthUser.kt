package com.eeszen.alumnidirectoryapp.data.model

// For AuthService
data class AuthUser(
    val uid: String,
    val displayName: String,
    val email: String,
    val photoUrl: String?
)
