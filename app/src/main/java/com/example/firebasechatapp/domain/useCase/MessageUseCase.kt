package com.example.firebasechatapp.domain.useCase

import com.example.firebasechatapp.common.Resource
import com.example.firebasechatapp.data.model.Message
import com.example.firebasechatapp.domain.repository.RealtimeRepository
import com.example.firebasechatapp.ui.presentation.message.MessageEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MessageUseCase @Inject constructor(
    private val repository: RealtimeRepository
) {
    operator fun invoke(event: MessageEvent): Flow<Resource<List<Message>>> {
        return when (event) {
            is MessageEvent.GetMessages -> {
                repository.getAllMessages(event.uid1, event.uid2).map { Resource.Success(it) }
            }
            is MessageEvent.SendMessage -> {
                val (uid1, uid2, msg) = event
                val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
                val dateTime = dateTimeFormat.format(Date())
                flow {
                    repository.addMessage(uid1, uid2, msg.copy(dateTime = dateTime))
                    emit(Resource.Success(listOf(), null))
                }
            }
        }
    }

//    operator fun invoke(uid1: String, uid2: String): Flow<List<Message>> {
//        return repository.getAllMessages(uid1, uid2)
//    }

//    suspend operator fun invoke(uid1: String, uid2: String, msg: Message) {
//        val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
//        val dateTime = dateTimeFormat.format(Date())
//        repository.addMessage(uid1, uid2, msg.copy(dateTime = dateTime))
//    }
}