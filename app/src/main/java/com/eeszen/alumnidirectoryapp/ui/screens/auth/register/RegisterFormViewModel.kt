package com.eeszen.alumnidirectoryapp.ui.screens.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.core.utils.SnackbarController
import com.eeszen.alumnidirectoryapp.core.utils.SnackbarEvent
import com.eeszen.alumnidirectoryapp.core.utils.validate
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.model.validation.UpdateUserForm
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class RegisterFormViewModel @Inject constructor(
    private val authService: AuthService,
    private val repo: AlumniRepo
): ViewModel() {
    private val _userData = MutableStateFlow(User())
    val userData = _userData.asStateFlow()
    private val _success = MutableSharedFlow<Unit>()
    val success = _success.asSharedFlow()
    private val _countries = MutableStateFlow<List<String>>(emptyList())
    val countries = _countries.asStateFlow()

    fun getCurrentUser() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val authUser = authService.getCurrentUser() ?: return@launch

                val baseUser = User(
                    id = authUser.uid,
                    fullName = authUser.displayName,
                    email = authUser.email
                )
                val firestoreUser = repo.getAlumniById(authUser.uid)
                _userData.value = firestoreUser ?: baseUser
            }
        }catch (e:Exception){
            viewModelScope.launch {
                SnackbarController.sendEvent(
                    SnackbarEvent(e.message.toString())
                )
            }
        }

    }
    fun getAuthUser() = authService.getCurrentUser()
    fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            _countries.value = repo.getCountries()
        }
    }

    fun submit(user: UpdateUserForm) {
        Log.d("debugging", "User: $user")
        val res = validate(user)
        if(res) {
            updateUser(user)
        } else {
            viewModelScope.launch {
                SnackbarController.sendEvent(
                    SnackbarEvent("All fields are required")
                )
            }
        }
    }

    fun updateUser(
        user: UpdateUserForm
    ) {
        val currentUser = authService.getCurrentUser() ?: return
        val uid = currentUser.uid

        val updatedUser = userData.value.copy(
            fullName = user.fullName,
            department = user.department,
            graduationYear = user.gradYear,
            currentJob = user.jobTitle,
            currentCompany = user.company,
            primaryTechStack = user.techStack,
            currentCountry = user.country,
            currentCity = user.city,
            contactPreference = user.contactPref,
            shortBio = user.shortBio
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.updateAlumni(uid, updatedUser)
                _success.emit(Unit)
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    SnackbarController.sendEvent(
                        SnackbarEvent(e.message ?: "Update failed")
                    )
                }
            }
        }

    }
}