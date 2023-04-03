package com.khayrul.firebasechatapp.data.repository

import com.google.firebase.firestore.CollectionReference
import com.khayrul.firebasechatapp.data.model.User
import com.khayrul.firebasechatapp.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(private val ref: CollectionReference): UserRepository {
    override suspend fun getUsers(): List<User> {
        val res = ref.get().await()
        val users = mutableListOf<User>()
        res.documents.forEach {
            it.toObject(User::class.java)?.let { user ->
                users.add(user)
            }
        }

        return users
    }
}

// Model, View, ViewModel