//package com.eeszen.alumnidirectoryapp.ui.screens.profile
//
//import android.net.Uri
//import android.util.Log
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
//import androidx.lifecycle.viewModelScope
//import com.eeszen.alumnidirectoryapp.core.utils.SnackbarController
//import com.eeszen.alumnidirectoryapp.core.utils.SnackbarEvent
//import com.eeszen.alumnidirectoryapp.core.utils.validate
//import com.eeszen.alumnidirectoryapp.data.model.Status
//import com.eeszen.alumnidirectoryapp.data.model.User
//import com.eeszen.alumnidirectoryapp.data.model.validation.UpdateUserForm
//import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
//import com.eeszen.alumnidirectoryapp.data.repo.UserRepo
//import com.eeszen.alumnidirectoryapp.service.AuthService
//import com.google.firebase.storage.FirebaseStorage
//import jakarta.inject.Inject
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asSharedFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//import kotlinx.coroutines.withContext
//import java.util.UUID
//
//@HiltViewModel
//class EditProfileViewModel @Inject constructor(
//    private val authService: AuthService,
//    private val repo: AlumniRepo,
//    private val savedStateHandle: SavedStateHandle,
//    private val userRepo: UserRepo
//): ViewModel() {
//    private val _user = MutableStateFlow(User())
//    val user = _user.asStateFlow()
//
//    private val _isAdmin = MutableStateFlow(false)
//    val isAdmin = _isAdmin.asStateFlow()
//
//    private val _success = MutableSharedFlow<Unit>()
//    val success = _success.asSharedFlow()
//
//    private val _error = MutableSharedFlow<String>()
//    val error = _error.asSharedFlow()
//
//    private val userId = savedStateHandle.get<String>("id")!!
//    init {
//        getUser(userId)
//        checkIsAdmin()
//    }
//    fun getUser(id: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repo.getAlumniById(id)?.let {
//                _user.value = it
//            }
//        }
//    }
//
//    fun checkIsAdmin() {
//        val uid = authService.getCurrentUser()?.uid ?: return
//        viewModelScope.launch(Dispatchers.IO) {
//            _isAdmin.value = userRepo.isAdmin(uid)
//        }
//    }
//
//    fun uploadAndUpdateProfilePhoto(imageUri: Uri) {
//        // Preview immediately (local state)
//        _user.value = _user.value.copy(profilePhoto = imageUri.toString())
//
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val currentUid = authService.getCurrentUid() ?: return@launch
//                val targetUserId = userId.ifEmpty { currentUid }
//
//                val storageRef = FirebaseStorage.getInstance()
//                    .reference
//                    .child("profile_photos/$targetUserId/${UUID.randomUUID()}")
//
//                storageRef.putFile(imageUri).await()
//                val downloadUrl = storageRef.downloadUrl.await().toString()
//
//                repo.updateUserPhoto(targetUserId, downloadUrl)
//
//                if (targetUserId == currentUid) {
//                    authService.updateAuthProfilePhoto(downloadUrl)
//                }
//
//                _user.value = _user.value.copy(profilePhoto = downloadUrl)
//            } catch (e: Exception) {
//                // Prevent crashing
//                _user.value = _user.value.copy(profilePhoto = _user.value.profilePhoto)
//                SnackbarController.sendEvent(
//                    SnackbarEvent(e.message.toString())
//                )
//            }
//        }
//    }
//
//
//    fun submit(user: UpdateUserForm) {
//        Log.d("debugging", "User: $user")
//        val res = validate(user)
//        if(res) {
//            updateUser(user)
//        } else {
//            viewModelScope.launch {
//                SnackbarController.sendEvent(
//                    SnackbarEvent("All fields are required")
//                )
//            }
//        }
//    }
//
//    fun updateUser(
//        userData: UpdateUserForm
//    ) {
//        val currentUser = authService.getCurrentUser() ?: return
//        val uid = currentUser.uid
//
//        val updatedUser = user.value.copy(
//            fullName = userData.fullName,
//            department = userData.department,
//            graduationYear = userData.gradYear,
//            currentJob = userData.jobTitle,
//            currentCompany = userData.company,
//            primaryTechStack = userData.techStack,
//            currentCountry = userData.country,
//            currentCity = userData.city,
//            contactPreference = userData.contactPref,
//            shortBio = userData.shortBio
//        )
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                repo.updateAlumni(uid, updatedUser)
//                _success.emit(Unit)
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main){
//                    SnackbarController.sendEvent(
//                        SnackbarEvent(e.message ?: "Update failed")
//                    )
//                }
//            }
//        }
//
//    }
//
//
////    fun updateUser(
////        fullName: String,
////        department: String,
////        gradYear: Int,
////        jobTitle: String,
////        company: String,
////        techStack: String,
////        country: String,
////        city: String,
////        contactPref: String,
////        shortBio: String,
////        status: Status
////    ) {
////
////
////        ){
////            viewModelScope.launch {
////                SnackbarController.sendEvent(
////                    SnackbarEvent("All fields are required")
////                )
////            }
////            return
////        }
////
////        val updatedUser = user.value.copy(
////            fullName = fullName,
////            department = department,
////            graduationYear = gradYear,
////            currentJob = jobTitle,
////            currentCompany = company,
////            primaryTechStack = techStack,
////            currentCountry = country,
////            currentCity = city,
////            contactPreference = contactPref,
////            shortBio = shortBio,
////            status = status
////        )
////        viewModelScope.launch(Dispatchers.IO) {
////            try {
////                repo.updateAlumni(
////                    id = userId,
////                    user = updatedUser
////                )
////                SnackbarController.sendEvent(
////                    SnackbarEvent("Profile edited successfully")
////                )
////                _success.emit(Unit)
////            }catch (e:Exception){
////                SnackbarController.sendEvent(
////                    SnackbarEvent(e.message.toString())
////                )
////            }
////        }
////    }
//}


// `app/src/main/java/com/eeszen/alumnidirectoryapp/ui/screens/profile/EditProfileViewModel.kt`
package com.eeszen.alumnidirectoryapp.ui.screens.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.core.utils.SnackbarController
import com.eeszen.alumnidirectoryapp.core.utils.SnackbarEvent
import com.eeszen.alumnidirectoryapp.core.utils.validate
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.model.validation.UpdateUserForm
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.data.repo.UserRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val repo: AlumniRepo,
    private val savedStateHandle: SavedStateHandle,
    private val userRepo: UserRepo
) : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _isAdmin = MutableStateFlow(false)
    val isAdmin = _isAdmin.asStateFlow()

    private val _success = MutableSharedFlow<Unit>()
    val success = _success.asSharedFlow()

    private val userId = savedStateHandle.get<String>("id") ?: ""

    init {
        if (userId.isNotBlank()) getUser(userId)
        checkIsAdmin()
    }

    fun getUser(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAlumniById(id)?.let { _user.value = it }
        }
    }

    fun checkIsAdmin() {
        val uid = authService.getCurrentUser()?.uid ?: return
        viewModelScope.launch(Dispatchers.IO) {
            _isAdmin.value = userRepo.isAdmin(uid)
        }
    }

    fun uploadAndUpdateProfilePhoto(imageUri: Uri) {
        _user.value = _user.value.copy(profilePhoto = imageUri.toString())

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUid = authService.getCurrentUid() ?: return@launch
                val targetUserId = if (userId.isNotBlank()) userId else currentUid

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
                SnackbarController.sendEvent(SnackbarEvent(e.message.toString()))
            }
        }
    }

    fun submit(user: UpdateUserForm) {
        Log.d("debugging", "User: $user")
        if (validate(user)) {
            updateUser(user)
        } else {
            viewModelScope.launch {
                SnackbarController.sendEvent(SnackbarEvent("All fields are required"))
            }
        }
    }

    private fun updateUser(userData: UpdateUserForm) {
        val currentUid = authService.getCurrentUid() ?: return
        val targetUserId = if (userId.isNotBlank()) userId else currentUid

        val updatedUser = user.value.copy(
            id = targetUserId,
            fullName = userData.fullName,
            department = userData.department,
            graduationYear = userData.gradYear,
            currentJob = userData.jobTitle,
            currentCompany = userData.company,
            primaryTechStack = userData.techStack,
            currentCountry = userData.country,
            currentCity = userData.city,
            contactPreference = userData.contactPref,
            shortBio = userData.shortBio,
            status = userData.status
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.updateAlumni(targetUserId, updatedUser)
                _success.emit(Unit)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    SnackbarController.sendEvent(SnackbarEvent(e.message ?: "Update failed"))
                }
            }
        }
    }
}
