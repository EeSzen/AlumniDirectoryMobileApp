package com.eeszen.alumnidirectoryapp.ui.screens.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: AlumniRepo,
    private val authService: AuthService,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _signOutSuccess = MutableSharedFlow<Unit>()
    val signOutSuccess = _signOutSuccess.asSharedFlow()

//    private val userId = savedStateHandle.get<String>("id")!!
    private val userId: String =
        savedStateHandle["id"] ?: authService.getCurrentUid() ?: ""

    init {
//        getAlumniById(userId)
        if (userId.isNotBlank()) {
            getAlumniById(userId)
        }
    }

    fun getAlumniById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAlumniById(id)?.let {
                _user.value = it
            }
        }
    }
    fun signOut() {
        authService.signOut()
        viewModelScope.launch {
            _signOutSuccess.emit(Unit)
        }
    }
    fun refresh() {
        getAlumniById(userId)
    }
    fun getAuthUser() = authService.getCurrentUser()
}