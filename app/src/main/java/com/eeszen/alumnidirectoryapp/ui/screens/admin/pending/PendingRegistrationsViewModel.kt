package com.eeszen.alumnidirectoryapp.ui.screens.admin.pending

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.data.model.Status
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PendingRegistrationsViewModel @Inject constructor(
    private val authService: AuthService,
    private val repo: AlumniRepo
): ViewModel() {
    private val _pendingUsers = MutableStateFlow<List<User>>(emptyList())
    val pendingUsers = _pendingUsers.asStateFlow()

    fun getPendingUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _pendingUsers.value = repo.getPendingAlumnis()
        }
    }

    fun approveUser(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateUserStatus(id, Status.APPROVED)
        }
        getPendingUsers()
    }

    fun rejectUser(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateUserStatus(id, Status.REJECTED)
        }
        getPendingUsers()
    }
}