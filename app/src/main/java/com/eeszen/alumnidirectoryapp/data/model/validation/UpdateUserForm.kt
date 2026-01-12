package com.eeszen.alumnidirectoryapp.data.model.validation

import com.eeszen.alumnidirectoryapp.data.model.Status

data class UpdateUserForm (
    val fullName: String,
    val department: String,
    val gradYear: Int,
    val jobTitle: String,
    val company: String,
    val techStack: String,
    val country: String,
    val city: String,
    val contactPref: String,
    val shortBio: String,
    val status: Status = Status.PENDING
)