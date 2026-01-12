package com.eeszen.alumnidirectoryapp.ui.screens.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class BottomSheetDialogViewModel @Inject constructor(
    val repo: AlumniRepo
): ViewModel() {
    private val _techStacks = MutableStateFlow<List<String>>(emptyList())
    val techStacks = _techStacks.asStateFlow()
    private val _countries = MutableStateFlow<List<String>>(emptyList())
    val countries = _countries.asStateFlow()

    fun getTechStacks() {
        viewModelScope.launch(Dispatchers.IO) {
            _techStacks.value = repo.getTechStacks()
        }
    }
    fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            _countries.value = repo.getCountries()
        }
    }
}