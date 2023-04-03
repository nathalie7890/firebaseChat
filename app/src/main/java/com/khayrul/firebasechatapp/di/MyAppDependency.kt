package com.khayrul.firebasechatapp.di

import com.khayrul.firebasechatapp.service.AuthService
import com.khayrul.firebasechatapp.data.repository.RealtimeRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khayrul.firebasechatapp.data.repository.UserRepositoryImpl
import com.khayrul.firebasechatapp.domain.repository.RealtimeRepository
import com.khayrul.firebasechatapp.domain.repository.UserRepository
import com.khayrul.firebasechatapp.domain.usecase.GetUsersUseCase
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
        return RealtimeRepositoryImpl()
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
        return UserRepositoryImpl(db.collection("users"))
    }
}