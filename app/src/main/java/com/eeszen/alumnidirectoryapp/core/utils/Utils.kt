package com.eeszen.alumnidirectoryapp.core.utils

import android.util.Log
import com.eeszen.alumnidirectoryapp.data.model.validation.UpdateUserForm
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackbarEvent(
    val msg: String
)

object SnackbarController{
    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent){
        _events.send(element = event)
    }
}

fun validate(
    user: UpdateUserForm
): Boolean {
    val res =  user.fullName.isNotBlank() &&
            user.department.isNotBlank() &&
            user.gradYear > 0 &&
            user.jobTitle.isNotBlank() &&
            user.company.isNotBlank() &&
            user.techStack.isNotBlank() &&
            user.country.isNotBlank() &&
            user.city.isNotBlank() &&
            user.contactPref.isNotBlank() &&
            user.shortBio.isNotBlank()
    Log.d("debugging", res.toString())
    return res
}