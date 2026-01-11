package com.eeszen.alumnidirectoryapp.ui.screens.auth.register

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eeszen.alumnidirectoryapp.core.utils.SnackbarController
import com.eeszen.alumnidirectoryapp.core.utils.SnackbarEvent
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
        val uid = currentUser.uid

        val updatedUser = userData.value.copy(
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
                id = uid,
                user = updatedUser
            )
            _success.emit(Unit)
        }
    }
}