package com.eeszen.alumnidirectoryapp.ui.screens.auth.register

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.alumnidirectoryapp.core.utils.SnackbarController
import com.eeszen.alumnidirectoryapp.core.utils.SnackbarEvent
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
//sealed class RegisterResult {
//    object GoHome : RegisterResult()
//    object GoRegisterForm : RegisterResult()
//}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService,
    private val repo: AlumniRepo = AlumniRepo.getInstance()
): ViewModel() {

    private val _success = MutableSharedFlow<Unit>()
    val success = _success.asSharedFlow()

    private val _successHome = MutableSharedFlow<Unit>()
    val successHome = _successHome.asSharedFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun signUpWithEmail(email: String, password: String) {
        val result = validate(email, password)
        if(result != null) {
            viewModelScope.launch {
                _error.emit(result)
                SnackbarController.sendEvent(SnackbarEvent(result))
            }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = authService.signUpWithEmail(email, password) ?: return@launch
                val uid = user.uid
                val userExist = repo.getAlumniById(uid)
                if(userExist != null) {
                    val newUser = User(
                        id = uid,
                        email = email
                    )
                    repo.addAlumni(newUser)
                    withContext(Dispatchers.Main) {
                        SnackbarController.sendEvent(SnackbarEvent("Welcome!"))
                    }
                }
                _success.emit(Unit)
            }catch (e:Exception){
                val msg = e.message ?: "Sign up Unsuccessful"
                Log.d("SignUpViewModel", msg, e)
                _error.emit(msg)
                withContext(Dispatchers.Main) {
                    SnackbarController.sendEvent(SnackbarEvent(msg))
                }
            }

        }
    }

    fun continueWithGoogle(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val signedIn = authService.signInWithGoogle(context)
                if (!signedIn) return@launch

                val uid = authService.getCurrentUid()
                    ?: throw IllegalStateException("Missing UID")

                val userExist = repo.getAlumniById(uid)


                if (userExist != null) {
                    Log.d("test", userExist.fullName)
                    viewModelScope.launch {
                        _successHome.emit(Unit)
                    }
                    withContext(Dispatchers.Main) {
                        SnackbarController.sendEvent(
                            SnackbarEvent("Account already exists. Please log in.")
                        )
                    }
                }

                val authUser = authService.getCurrentUser()
                    ?: throw IllegalStateException("Missing user")

                repo.addAlumni(
                    User(
                        id = uid,
                        fullName = authUser.displayName,
                        email = authUser.email
                    )
                )


                withContext(Dispatchers.Main) {
                    _success.emit(Unit)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    SnackbarController.sendEvent(
                        SnackbarEvent(e.message ?: "Google sign up unsuccessful")
                    )
                }
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