package com.example.firebasechatapp.domain.repository

import com.example.firebasechatapp.data.model.Message
import kotlinx.coroutines.flow.Flow

interface RealtimeRepository {
    suspend fun addMessage(msg: Message)

    suspend fun addMessage(uid1: String, uid2: String, msg: Message)

    fun getAllMessages(uid1: String, uid2: String): Flow<List<Message>>

    fun getAllMessages(): Flow<List<Message>>
}