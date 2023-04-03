package com.khayrul.firebasechatapp.ui.presentation.login

import com.khayrul.firebasechatapp.data.model.User

sealed class LoginEvent {
    data class Login(val user: User): LoginEvent()
}
