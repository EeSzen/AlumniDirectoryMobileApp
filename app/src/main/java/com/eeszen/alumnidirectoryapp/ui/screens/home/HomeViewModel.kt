package com.eeszen.alumnidirectoryapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.data.model.Status
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.data.repo.UserRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import com.eeszen.alumnidirectoryapp.ui.screens.dialog.FilterState
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
    private val _userStatus = MutableStateFlow(Status.PENDING)
    val userStatus = _userStatus.asStateFlow()
    private val _alumni = MutableStateFlow<List<User>>(emptyList())
    private val _filteredAlumni = MutableStateFlow<List<User>>(emptyList())
    val filteredAlumni = _filteredAlumni.asStateFlow()
    private val _searchName = MutableStateFlow("")
    val searchName = _searchName.asStateFlow()
    private val _sortOption = MutableStateFlow(SortOption.NAME_AZ)
    val sortOption = _sortOption.asStateFlow()
    private val _filters = MutableStateFlow(FilterState())

    init {
        authService.getCurrentUser()?.uid?.let { uid ->
            loggedInUserStatus(uid)
        }
    }
    fun onSearchName(name: String) {
        _searchName.value = name
        applyFilters()
    }
    fun getAllAlumni() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getApprovedAlumnis() // display approved alumni profiles only
            _alumni.value = result
            _filteredAlumni.value = result // initial state with all alumni
            applyFilters()
        }
    }
    fun loggedInUserStatus(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val status = userRepo.getUserStatus(id)
            _userStatus.value = status ?: Status.PENDING
        }
    }
    private fun applyFilters() {
        val query = _searchName.value.trim().lowercase()
        var list = _alumni.value // all alumni
        val filters = _filters.value

        if (query.isNotBlank()) {
            list = list.filter {
                it.fullName.lowercase().contains(query)
            }
        }
        list = when (_sortOption.value) {
            SortOption.NAME_AZ ->
                list.sortedBy { it.fullName.lowercase() }
            SortOption.NAME_ZA ->
                list.sortedByDescending { it.fullName.lowercase() }
            SortOption.GRAD_YEAR_NEWEST ->
                list.sortedByDescending { it.graduationYear }
            SortOption.GRAD_YEAR_OLDEST ->
                list.sortedBy { it.graduationYear }
            SortOption.RECENTLY_UPDATED ->
                list.sortedByDescending { it.updatedAt }
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
        _filteredAlumni.value = list
    }
    fun onSortOptionSelected(option: SortOption) {
        _sortOption.value = option
        applyFilters()
    }
    fun onFiltersChanged(filters: FilterState) {
        _filters.value = filters
        applyFilters()
    }
}
enum class SortOption {
    NAME_AZ,
    NAME_ZA,
    GRAD_YEAR_NEWEST,
    GRAD_YEAR_OLDEST,
    RECENTLY_UPDATED
}