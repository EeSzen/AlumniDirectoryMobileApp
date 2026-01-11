package com.eeszen.alumnidirectoryapp.data.repo

import com.eeszen.alumnidirectoryapp.data.model.Status
import com.eeszen.alumnidirectoryapp.data.model.User
import kotlinx.coroutines.flow.Flow

interface AlumniRepo {
    suspend fun getAllAlumnis():List<User>
    suspend fun getAllAlumnisFlow(): Flow<List<User>>
    suspend fun getAlumniById(id:String):User?
    suspend fun addAlumni(user: User)
    suspend fun deleteAlumni(id:String)
    suspend fun updateAlumni(id:String,user: User)
    suspend fun getApprovedAlumnis(): List<User>
    suspend fun getPendingAlumnis(): List<User>
    suspend fun getRejectedAlumnis(): List<User>
    suspend fun updateUserStatus(id: String, status: Status)
    suspend fun getRecentApprovedUsers(days: Int = 7): List<User>

    //Metadata
    suspend fun getTechStacks(): List<String>
    suspend fun getCountries(): List<String>

    companion object {
        private var instance: AlumniRepo? = null

        fun getInstance(): AlumniRepo{
            if(instance == null){
                instance = AlumniRepoFireImpl()
            }
            return instance!!
        }
    }
}