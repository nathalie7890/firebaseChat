package com.khayrul.firebasechatapp.ui.presentation.message

import com.khayrul.firebasechatapp.data.model.Message

sealed class MessageEvent {
    data class SendMessage(
        val uid1: String,
        val uid2: String,
        val msg: Message
    ) : MessageEvent()
    data class GetMessages(
        val uid1: String,
        val uid2: String
    ) : MessageEvent()
}
