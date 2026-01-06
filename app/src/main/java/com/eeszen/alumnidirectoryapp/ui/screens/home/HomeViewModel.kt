package com.eeszen.alumnidirectoryapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.data.model.Status
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.data.repo.UserRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: AlumniRepo,
    private val authService: AuthService,
    private val userRepo: UserRepo
): ViewModel() {
    private val _alumni = MutableStateFlow<List<User>>(emptyList())
    val alumni = _alumni.asStateFlow()

    private val _userStatus = MutableStateFlow(Status.PENDING)
    val userStatus = _userStatus.asStateFlow()

    init {
        authService.getCurrentUser()?.uid?.let { uid ->
            loggedInUserStatus(uid)
        }
    }
    fun getAllAlumni() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getAllAlumnis()
            _alumni.value = result
        }
    }
    fun loggedInUserStatus(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val status = userRepo.getUserStatus(id)
            _userStatus.value = status ?: Status.PENDING
        }
    }
}