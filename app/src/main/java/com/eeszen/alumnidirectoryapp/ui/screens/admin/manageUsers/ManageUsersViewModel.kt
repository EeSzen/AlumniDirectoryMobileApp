package com.eeszen.alumnidirectoryapp.ui.screens.admin.manageUsers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import com.eeszen.alumnidirectoryapp.ui.screens.dialog.FilterState
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
    private val _filteredUsers = MutableStateFlow<List<User>>(emptyList())
    val filteredUsers = _filteredUsers.asStateFlow()
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    private val _filters = MutableStateFlow(FilterState())
    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getAllAlumnis()
            _allUsers.value = result
            _filteredUsers.value = result
            applyFilters()
        }
    }
    fun onSearchChange(query: String) {
        _searchQuery.value = query
        applyFilters()
    }
    fun onFiltersChanged(filters: FilterState) {
        _filters.value = filters
        applyFilters()
    }
    fun applyFilters() {
        val query = _searchQuery.value.trim().lowercase()
        var list = _allUsers.value
        val filters = _filters.value

        if (query.isNotBlank()) {
            list = list.filter {
                it.fullName.lowercase().contains(query) ||
                        it.status.toString().lowercase().contains(query)
            }
        }
        // Tech stack
        if (filters.techStacks.isNotEmpty()) {
            list = list.filter {
                filters.techStacks.contains(it.primaryTechStack)
            }
        }
        // Country
        if (filters.countries.isNotEmpty()) {
            list = list.filter {
                filters.countries.contains(it.currentCountry)
            }
        }
        // Graduation year
        if (filters.gradYears.isNotEmpty()) {
            list = list.filter {
                filters.gradYears.contains(it.graduationYear)
            }
        }
        _filteredUsers.value = list
    }
}