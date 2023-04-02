package com.example.firebasechatapp.ui.presentation.register

import com.example.firebasechatapp.data.model.User

sealed class SignupEvent {
    data class signup(val user: User) : SignupEvent()
}
