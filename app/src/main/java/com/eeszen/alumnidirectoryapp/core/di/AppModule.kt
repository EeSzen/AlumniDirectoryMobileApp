package com.eeszen.alumnidirectoryapp.core.di

import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepoFireImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAuthService(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
    @Provides
    @Singleton
    fun provideRepo(): AlumniRepo {
        return AlumniRepoFireImpl()
    }
}