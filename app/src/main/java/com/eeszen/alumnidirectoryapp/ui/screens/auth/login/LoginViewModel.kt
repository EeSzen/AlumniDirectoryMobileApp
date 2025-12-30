package com.eeszen.alumnidirectoryapp.ui.screens.auth.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.service.AuthService
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService
): ViewModel() {
    private val _success = MutableSharedFlow<Unit>()
    val success = _success.asSharedFlow()
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun signInWithEmail(email: String, password: String) {
        val result = validate(email, password)
        if(result != null) {
            viewModelScope.launch {
                _error.emit(result)
            }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            authService.signInWithEmail(email, password)
            _success.emit(Unit)
        }
    }
    fun signInWithGoogle(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = authService.signInWithGoogle(context)
            if(result) {
                _success.emit(Unit)
            }
        }
    }
    fun validate(email: String, password: String): String? {
        return try {
            require(email.isNotBlank()) {"Email is required"}
            require(password.isNotBlank()) {"Password is required"}
            null
        } catch (e: Exception) {
            Log.d("debugging", e.message.toString())
            e.message.toString()
        }
    }
}