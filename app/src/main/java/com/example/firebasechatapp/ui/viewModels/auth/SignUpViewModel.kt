package com.example.firebasechatapp.ui.viewModels.auth

import com.example.firebasechatapp.ui.viewModels.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

class SignUpViewModel: BaseViewModel() {
    val signupFinish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun signup() {}
}