package com.eeszen.alumnidirectoryapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val repo: AlumniRepo
): ViewModel() {
    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()
    private val _success = MutableSharedFlow<Unit>()
    val success = _success.asSharedFlow()

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = authService.getCurrentUser() ?: return@launch
            repo.getAlumniById(currentUser.uid)?.let {
                _user.value = it
            }
        }
    }
    fun getAuthUser() = authService.getCurrentUser()
    fun updateUser(
        fullName: String,
        department: String,
        gradYear: Int,
        jobTitle: String,
        company: String,
        techStack: String,
        country: String,
        city: String,
        contactPref: String,
        shortBio: String
    ) {
        val currentUser = authService.getCurrentUser() ?: return

        val updatedUser = user.value.copy(
            fullName = fullName,
            department = department,
            graduationYear = gradYear,
            currentJob = jobTitle,
            currentCompany = company,
            primaryTechStack = techStack,
            currentCountry = country,
            currentCity = city,
            contactPreference = contactPref,
            shortBio = shortBio
        )
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateAlumni(
                id = currentUser.uid,
                user = updatedUser
            )
            _success.emit(Unit)
        }
    }
}