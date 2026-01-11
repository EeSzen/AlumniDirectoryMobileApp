package com.eeszen.alumnidirectoryapp.data.repo

import android.util.Log
import com.eeszen.alumnidirectoryapp.data.model.Status
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.service.AuthService
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AlumniRepoFireImpl:AlumniRepo {
    val db = FirebaseFirestore.getInstance()
    val authService = AuthService.getInstance()

    private fun getAlumnisCollection():CollectionReference{
//        val user = authService.getCurrentUser()
//        if (user == null) throw Exception("User doesn't exist")
//        require(user != null){ "User doesn't exist" }
        return db.collection("users")
    }

    override suspend fun getAllAlumnis(): List<User> {
        return try{
            val snapshot = getAlumnisCollection()
                .get().await()
            snapshot.documents.mapNotNull {
                it.toObject(User::class.java)
                    ?.copy(id=it.id)
            }
        }catch (e:Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getAllAlumnisFlow() = callbackFlow {
        val listener = getAlumnisCollection().addSnapshotListener{ value, error ->
            if (error != null) {
                Log.d("AlumniRepo",error.message.toString())
                throw error
            }
            val users = mutableListOf<User>()
            value?.documents?.let { items ->
                for (item in items){
                    item.toObject(User::class.java)?.let {
                        users.add(it.copy(id = item.id))
                    }
                }
            }
            trySend(users) // similar to emit function
        }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun getAlumniById(id: String): User? {
        val snapshot = getAlumnisCollection()
            .document(id).get().await()

        return snapshot.toObject(User::class.java)
            ?.copy(id=snapshot.id)
    }

    override suspend fun addAlumni(user: User) {
        getAlumnisCollection()
            .document(user.id).set(user).await()
    }

    override suspend fun deleteAlumni(id: String) {
        getAlumnisCollection().document(id)
            .delete().await()
    }

    override suspend fun updateAlumni(id: String, user: User) {
        getAlumnisCollection().document(id)
            .set(user).await()
    }

    override suspend fun getApprovedAlumnis(): List<User> {
        return try{
            val snapshot = getAlumnisCollection()
                .whereEqualTo("status", Status.APPROVED.name)
                .get().await()
            snapshot.documents.mapNotNull {
                it.toObject(User::class.java)
                    ?.copy(id=it.id)
            }
        }catch (e:Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getPendingAlumnis(): List<User> {
        return try{
            val snapshot = getAlumnisCollection()
                .whereEqualTo("status", Status.PENDING.name)
                .get().await()
            snapshot.documents.mapNotNull {
                it.toObject(User::class.java)
                    ?.copy(id=it.id)
            }
        }catch (e:Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getRejectedAlumnis(): List<User> {
        return try{
            val snapshot = getAlumnisCollection()
                .whereEqualTo("status", Status.REJECTED.name)
                .get().await()
            snapshot.documents.mapNotNull {
                it.toObject(User::class.java)
                    ?.copy(id=it.id)
            }
        }catch (e:Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun updateUserStatus(
        id: String,
        status: Status
    ) {
        try {
            getAlumnisCollection()
                .document(id)
                .update("status", status.name)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun updateUserPhoto(id: String, photoUrl: String) {
        getAlumnisCollection()
            .document(id)
            .update("profilePhoto", photoUrl)
            .await()
    }

    //Metadata
    override suspend fun getTechStacks(): List<String> {
        val snap =  db.collection("metadata")
            .document("techStacks")
            .get()
            .await()
        return snap.get("values") as? List<String> ?: emptyList()
    }

    override suspend fun getCountries(): List<String> {
        val snap =  db.collection("metadata")
            .document("countries")
            .get()
            .await()
        return snap.get("values") as? List<String> ?: emptyList()
    }
}