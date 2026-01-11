package com.eeszen.alumnidirectoryapp.ui.screens.auth.register

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService,
    private val repo: AlumniRepo = AlumniRepo.getInstance()
): ViewModel() {
    private val _success = MutableSharedFlow<Unit>()
    val success = _success.asSharedFlow()
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun signUpWithEmail(email: String, password: String) {
        val result = validate(email, password)
        if(result != null) {
            viewModelScope.launch {
                _error.emit(result)
            }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val user = authService.signUpWithEmail(email, password) ?: return@launch
            val uid = user.uid
            val userExist = repo.getAlumniById(uid)

            if(userExist == null) {
                val newUser = User(
                    id = uid,
                    email = email
                )
                repo.addAlumni(newUser)
            }
            _success.emit(Unit)
        }
    }
    fun continueWithGoogle(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = authService.signInWithGoogle(context)
            if(!result) return@launch

            val uid = authService.getCurrentUid() ?: return@launch
            val userExist = repo.getAlumniById(uid)

            if(userExist == null) {
                val authUser = authService.getCurrentUser() ?: return@launch
                val newUser = User(
                    id = uid,
                    fullName = authUser.displayName,
                    email = authUser.email,
//                    profilePhoto = authUser.photoUrl ?: ""
                )
                repo.addAlumni(newUser)
            }
            _success.emit(Unit)
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