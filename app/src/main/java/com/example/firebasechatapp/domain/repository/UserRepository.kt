package com.example.firebasechatapp.domain.repository

import com.example.firebasechatapp.data.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
}