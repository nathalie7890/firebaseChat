package com.example.firebasechatapp.ui.presentation.login.viewModel

import androidx.lifecycle.viewModelScope
import com.example.firebasechatapp.common.Resource
import com.example.firebasechatapp.data.model.User
import com.example.firebasechatapp.domain.useCase.LoginUseCase
import com.example.firebasechatapp.service.AuthService
import com.example.firebasechatapp.ui.presentation.base.viewModel.BaseViewModel
import com.example.firebasechatapp.ui.presentation.login.LoginEvent
import com.example.firebasechatapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : BaseViewModel() {
    val loginFinish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val email: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = MutableStateFlow("")

    fun login() {
        loginUseCase(
            LoginEvent.Login(
                User(
                    email = email.value,
                    password = password.value
                )
            )
        ).onEach {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    loginFinish.emit(Unit)
                }
                is Resource.Error -> {
                    error.emit(it.message!!)
                }
            }
        }.launchIn(viewModelScope)
    }
}