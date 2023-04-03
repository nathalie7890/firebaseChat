package com.example.firebasechatapp.ui.presentation.login

import com.example.firebasechatapp.data.model.User

sealed class LoginEvent {
    data class Login(val user: User) : LoginEvent()
}
