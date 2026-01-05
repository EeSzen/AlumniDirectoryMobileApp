package com.eeszen.alumnidirectoryapp.ui.screens.admin.manageUsers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ManageUsersViewModel @Inject constructor(
    private val authService: AuthService,
    private val repo: AlumniRepo
): ViewModel() {

    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers = _allUsers.asStateFlow()

    private val _filteredUsers = MutableStateFlow<List<User>>(emptyList())
    val filteredUsers = _filteredUsers.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getAllAlumnis()
            _allUsers.value = result
            _filteredUsers.value = result
        }
    }

    fun onSearchChange(query: String) {
        _searchQuery.value = query
        applyFilters()
    }

    fun applyFilters() {
        val query = _searchQuery.value.trim().lowercase()
        _filteredUsers.value = _allUsers.value.filter { user ->
            query.isBlank() ||
                    user.fullName.lowercase().contains(query) ||
                    user.email.lowercase().contains(query) ||
                    user.primaryTechStack.lowercase().contains(query) ||
                    user.currentCompany.lowercase().contains(query)
        }
    }
}