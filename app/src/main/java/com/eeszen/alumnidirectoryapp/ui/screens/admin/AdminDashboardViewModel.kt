package com.eeszen.alumnidirectoryapp.ui.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(
    private val authService: AuthService,
    private val repo: AlumniRepo
): ViewModel() {
    private val _pendingUserCount = MutableStateFlow(0)
    val pendingUserCount = _pendingUserCount.asStateFlow()

    private val _approvedUserCount = MutableStateFlow(0)
    val approvedUserCount = _approvedUserCount.asStateFlow()
    private val _rejectedUserCount = MutableStateFlow(0)
    val rejectedUserCount = _rejectedUserCount.asStateFlow()

    private val _allUsersCount = MutableStateFlow(0)
    val allUsersCount = _allUsersCount.asStateFlow()
    private val _recentApprovedCount = MutableStateFlow(0)
    val recentApprovedCount = _recentApprovedCount.asStateFlow()

    fun getPendingUsersCount() {
        viewModelScope.launch(Dispatchers.IO) {
            _pendingUserCount.value = repo.getPendingAlumnis().size
        }
    }

    fun getApprovedUsersCount() {
        viewModelScope.launch(Dispatchers.IO) {
            _approvedUserCount.value = repo.getApprovedAlumnis().size
        }
    }

    fun getRejectedUsersCount() {
        viewModelScope.launch(Dispatchers.IO) {
            _rejectedUserCount.value = repo.getRejectedAlumnis().size
        }
    }
    fun getAllUsersCount() {
        viewModelScope.launch(Dispatchers.IO) {
            _allUsersCount.value = repo.getAllAlumnis().size
        }
    }
    fun getRecentApprovals() {
        viewModelScope.launch(Dispatchers.IO) {
            _recentApprovedCount.value =
                repo.getRecentApprovedUsers().size
        }
    }
}