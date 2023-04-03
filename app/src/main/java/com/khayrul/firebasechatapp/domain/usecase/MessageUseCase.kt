package com.khayrul.firebasechatapp.domain.usecase

import com.khayrul.firebasechatapp.common.Resource
import com.khayrul.firebasechatapp.data.model.Message
import com.khayrul.firebasechatapp.domain.repository.RealtimeRepository
import com.khayrul.firebasechatapp.ui.presentation.message.MessageEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MessageUseCase @Inject constructor(
    private val realtimeRepository: RealtimeRepository
) {

    operator fun invoke(event: MessageEvent): Flow<Resource<List<Message>>> {
        return when(event) {
            is MessageEvent.GetMessages -> {
                realtimeRepository
                    .getAllMessages(event.uid1, event.uid2)
                    .map { Resource.Success(it) }
            }

            is MessageEvent.SendMessage -> {
                val (uid1, uid2, msg) = event
                val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH)
                val date = formatter.format(Date())
                flow {
                    realtimeRepository.addMessage(uid1, uid2, msg.copy(dateTime = date))
                    emit(Resource.Success(listOf(), null))
                }
            }
        }
    }

//    operator fun invoke(uid1: String, uid2: String): Flow<List<Message>> {
//            return realtimeRepository.getAllMessages(uid1, uid2)
//    }
//
//    suspend operator fun invoke(uid1: String, uid2: String, msg: Message) {
//        val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH)
//        val date = formatter.format(Date())
//        realtimeRepository.addMessage(uid1, uid2, msg.copy(dateTime = date))
//    }
}