package com.eeszen.alumnidirectoryapp.data.repo


import com.eeszen.alumnidirectoryapp.data.model.Status
import com.eeszen.alumnidirectoryapp.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepo(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val users = db.collection("users")

    suspend fun createPendingUserProfile(
        user: User
    ) {
        require(user.id.isNotBlank()) { "User.id (uid) is required" }
        users.document(user.id).set(user).await()
    }

    suspend fun getUser(uid: String): User? {
        val snap = users.document(uid).get().await()
        return snap.toObject(User::class.java)
    }

    suspend fun getUserStatus(uid: String): Status? {
        return getUser(uid)?.status
    }
}