package com.khayrul.firebasechatapp.model.repositories

import com.google.firebase.firestore.CollectionReference
import com.khayrul.firebasechatapp.model.model.User
import kotlinx.coroutines.tasks.await

class UserRepository(private val ref: CollectionReference) {
    suspend fun getUsers(): List<User> {
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