package com.example.firebasechatapp.data.repository

import com.example.firebasechatapp.data.model.User
import com.example.firebasechatapp.domain.repository.UserRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class UserRepositoryImplementation(private val ref: CollectionReference): UserRepository {
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