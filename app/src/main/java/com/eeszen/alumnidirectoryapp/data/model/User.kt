package com.eeszen.alumnidirectoryapp.data.model

data class User (
    val id: String = "",
    val fullName: String = "",
    val password: String = "",
    val email: String = "",

    val graduationYear: Int = 0,
    val department: String = "",

    val currentJob: String = "",
    val currentCompany: String = "",

    val primaryTechStack: String = "",
    val skills: List<String> = emptyList(),

    val currentCountry: String = "",
    val currentCity: String = "",

    val contactPreference: String = "",
    val shortBio: String = "",
    val profilePhoto: String = "",

    val status: Status = Status.PENDING,
    val role: Role = Role.ALUMNI,

    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val approvedAt: Long? = null
)   
enum class Status {
    PENDING, APPROVED, REJECTED, INACTIVE
}
enum class Role {
    ALUMNI, ADMIN
}
