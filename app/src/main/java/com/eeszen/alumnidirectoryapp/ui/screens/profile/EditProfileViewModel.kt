package com.eeszen.alumnidirectoryapp.ui.screens.profile

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.core.utils.SnackbarController
import com.eeszen.alumnidirectoryapp.core.utils.SnackbarEvent
import com.eeszen.alumnidirectoryapp.data.model.Status
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.data.repo.UserRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import com.google.firebase.storage.FirebaseStorage
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val repo: AlumniRepo,
    private val savedStateHandle: SavedStateHandle,
    private val userRepo: UserRepo
): ViewModel() {
    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _isAdmin = MutableStateFlow(false)
    val isAdmin = _isAdmin.asStateFlow()

    private val _success = MutableSharedFlow<Unit>()
    val success = _success.asSharedFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    private val userId = savedStateHandle.get<String>("id")!!
    init {
        getUser(userId)
        checkIsAdmin()
    }
    fun getUser(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAlumniById(id)?.let {
                _user.value = it
            }
        }
    }

    fun checkIsAdmin() {
        val uid = authService.getCurrentUser()?.uid ?: return
        viewModelScope.launch(Dispatchers.IO) {
            _isAdmin.value = userRepo.isAdmin(uid)
        }
    }

    fun uploadAndUpdateProfilePhoto(imageUri: Uri) {
        // Preview immediately (local state)
        _user.value = _user.value.copy(profilePhoto = imageUri.toString())

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUid = authService.getCurrentUid() ?: return@launch
                val targetUserId = userId.ifEmpty { currentUid }

                val storageRef = FirebaseStorage.getInstance()
                    .reference
                    .child("profile_photos/$targetUserId/${UUID.randomUUID()}")

                storageRef.putFile(imageUri).await()
                val downloadUrl = storageRef.downloadUrl.await().toString()

                repo.updateUserPhoto(targetUserId, downloadUrl)

                if (targetUserId == currentUid) {
                    authService.updateAuthProfilePhoto(downloadUrl)
                }

                _user.value = _user.value.copy(profilePhoto = downloadUrl)
            } catch (e: Exception) {
                // Prevent crashing
                _user.value = _user.value.copy(profilePhoto = _user.value.profilePhoto)
                SnackbarController.sendEvent(
                    SnackbarEvent(e.message.toString())
                )
            }
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
        shortBio: String,
        status: Status
    ) {
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
            shortBio = shortBio,
            status = status
        )
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateAlumni(
                id = userId,
                user = updatedUser
            )
            _success.emit(Unit)
        }
    }
}