package com.khayrul.firebasechatapp.ui.presentation.login.viewModel

import androidx.lifecycle.viewModelScope
import com.khayrul.firebasechatapp.common.Resource
import com.khayrul.firebasechatapp.data.model.User
import com.khayrul.firebasechatapp.domain.usecase.LoginUseCase
import com.khayrul.firebasechatapp.service.AuthService
import com.khayrul.firebasechatapp.ui.presentation.base.viewModel.BaseViewModel
import com.khayrul.firebasechatapp.ui.presentation.login.LoginEvent
import com.khayrul.firebasechatapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {
    val loginFinish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val email: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = MutableStateFlow("")

    fun login() {
        loginUseCase(
            LoginEvent.Login(
                User(email = email.value, password = password.value)
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