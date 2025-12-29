package com.eeszen.alumnidirectoryapp.service

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.eeszen.alumnidirectoryapp.data.model.AuthUser
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await

class AuthService private constructor() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val _authuser = MutableStateFlow<AuthUser?>(null)
    val authuser = _authuser.asStateFlow()

    init {
        firebaseAuth.currentUser?.let { updateUser(it) }
    }

    private fun updateUser(firebaseUser:FirebaseUser){
        _authuser.update {
            AuthUser(
                firebaseUser.uid,
                firebaseUser.displayName ?: "Unknown",
                firebaseUser.email ?: "Unknown",
                firebaseUser.photoUrl.toString()
            )
        }
    }

    // Email
    suspend fun signUpWithEmail(email: String, password: String): FirebaseUser? {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        result.user?.let { updateUser(it) }
        return result.user
    }

    suspend fun signInWithEmail(email: String, password: String): FirebaseUser? {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        result.user?.let { updateUser(it) }
        return result.user
    }


    // Google
    suspend fun signInWithGoogle(context:Context){
        try {
            val token = getGoogleCredential(context)
            val credential = GoogleAuthProvider.getCredential(token,null)
            val result = firebaseAuth.signInWithCredential(credential).await()

            result.user?.let { updateUser(it)}
        }catch (e:GetCredentialCancellationException){
            Log.d("AuthService","Sign-in was cancelled",e)
        }catch (e: GetCredentialException){
            Log.d("AuthService","Google Sign-in failed",e)
        }catch (e:Exception){
            Log.d("AuthService","Sign-in failed",e)
        }
    }

    fun signOut(){
        firebaseAuth.signOut()
        _authuser.value = null
    }

    private suspend fun getGoogleCredential(context: Context): String?{
        val credentialManager = CredentialManager.create(context)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("448892257654-s599rpt6hcqjl5fbv51mq7iokdinrssd.apps.googleusercontent.com")
            .build()
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            result.credential.data.getString(
                "com.google.android.libraries.identity.googleid.BUNDLE_KEY_ID_TOKEN"
            )
        } catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    fun getCurrentUser():AuthUser? {
        return authuser.value
    }

    fun getCurrentUid(): String? = firebaseAuth.currentUser?.uid

    companion object {
        private var instance: AuthService?=null

        fun getInstance():AuthService{
            if (instance == null){
                instance = AuthService()
            }
            return instance!!
        }
    }
}