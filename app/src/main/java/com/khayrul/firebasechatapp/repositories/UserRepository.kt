package com.khayrul.firebasechatapp.repositories

import com.khayrul.firebasechatapp.data.model.User

class UserRepository {
    suspend fun getUserByEmail(email: String): User {
        TODO()
    }
}