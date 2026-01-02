package com.eeszen.alumnidirectoryapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: AlumniRepo
): ViewModel() {
    private val _alumni = MutableStateFlow<List<User>>(emptyList())
    val alumni = _alumni.asStateFlow()

    fun getAllAlumni() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getAllAlumnis()
            _alumni.value = result
        }
    }
}