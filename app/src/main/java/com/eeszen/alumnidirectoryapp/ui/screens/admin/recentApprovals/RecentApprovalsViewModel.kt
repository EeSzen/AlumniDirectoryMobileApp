package com.eeszen.alumnidirectoryapp.ui.screens.admin.recentApprovals

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RecentApprovalsViewModel @Inject constructor(
    private val authService: AuthService,
    private val repo: AlumniRepo
): ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    fun getRecentApprovedUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _users.value = repo.getRecentApprovedUsers()
        }
    }
}