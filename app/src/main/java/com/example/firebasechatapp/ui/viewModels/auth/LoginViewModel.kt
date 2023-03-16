package com.example.firebasechatapp.ui.viewModels.auth

import com.example.firebasechatapp.ui.viewModels.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

class LoginViewModel(): BaseViewModel() {
    val loginFinish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun login() {}
}