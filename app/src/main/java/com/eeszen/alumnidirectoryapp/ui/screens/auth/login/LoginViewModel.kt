package com.eeszen.alumnidirectoryapp.ui.screens.auth.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.core.utils.SnackbarController
import com.eeszen.alumnidirectoryapp.core.utils.SnackbarEvent
import com.eeszen.alumnidirectoryapp.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    private val _success = MutableSharedFlow<Unit>()
    val success = _success.asSharedFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun signInWithEmail(email: String, password: String) {
        val validationMsg = validate(email, password)
        if (validationMsg != null) {
            viewModelScope.launch(Dispatchers.Main) {
                _error.emit(validationMsg)
                SnackbarController.sendEvent(SnackbarEvent(validationMsg))
            }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                authService.signInWithEmail(email, password)
                _success.emit(Unit)
            } catch (t: Throwable) {
                val msg = t.message ?: "Sign in failed"
                Log.d("LoginViewModel", msg, t)
                _error.emit(msg)
                withContext(Dispatchers.Main) {
                    SnackbarController.sendEvent(SnackbarEvent(msg))
                }
            }
        }
    }

    fun signInWithGoogle(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val ok = authService.signInWithGoogle(context)
                if (ok) _success.emit(Unit)
            } catch (t: Throwable) {
                val msg = t.message ?: "Google sign in failed"
                Log.d("LoginViewModel", msg, t)
                _error.emit(msg)
                withContext(Dispatchers.Main) {
                    SnackbarController.sendEvent(SnackbarEvent(msg))
                }
            }
        }
    }

    fun validate(email: String, password: String): String? {
        return try {
            require(email.isNotBlank()) { "Email is required" }
            require(password.isNotBlank()) { "Password is required" }
            null
        } catch (t: Throwable) {
            Log.d("LoginViewModel", t.message.orEmpty(), t)
            t.message ?: "Invalid input"
        }
    }
}
