package com.example.firebasechatapp.di

import com.example.firebasechatapp.service.AuthService
import com.example.firebasechatapp.model.repositories.RealtimeRepository
import com.example.firebasechatapp.model.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyAppDependency {

    @Provides
    @Singleton
    fun provideRealtimeDatabase(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("chat-app")
    }

    @Provides
    @Singleton
    fun getRealtimeRepository(): RealtimeRepository {
        return RealtimeRepository()
    }

    @Provides
    @Singleton
    fun getFireStore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun getFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun getAuthRepository(auth: FirebaseAuth, db: FirebaseFirestore): AuthService {
        return AuthService(auth, db.collection("users"))
    }

    @Provides
    @Singleton
    fun provideUserRepository(db: FirebaseFirestore): UserRepository {
        return UserRepository(db.collection("users"))
    }
}