package com.example.firebasechatapp.repositories

import com.example.firebasechatapp.data.model.User
import com.google.firebase.firestore.CollectionReference
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