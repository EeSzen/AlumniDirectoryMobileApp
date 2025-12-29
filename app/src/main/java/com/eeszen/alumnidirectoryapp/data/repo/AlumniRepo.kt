package com.eeszen.alumnidirectoryapp.data.repo

import com.eeszen.alumnidirectoryapp.data.model.User
import kotlinx.coroutines.flow.Flow

interface AlumniRepo {
    suspend fun getAllAlumnis():List<User>
    suspend fun getAllAlumnisFlow(): Flow<List<User>>
    suspend fun getAlumniById(id:String):User?
    suspend fun addAlumni(user: User)
    suspend fun deleteAlumni(id:String)
    suspend fun updateAlumni(id:String,user: User)

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