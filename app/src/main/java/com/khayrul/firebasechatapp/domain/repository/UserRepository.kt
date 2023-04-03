package com.khayrul.firebasechatapp.domain.repository

import com.khayrul.firebasechatapp.data.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
}