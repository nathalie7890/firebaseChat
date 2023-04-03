package com.khayrul.firebasechatapp.domain.usecase

import com.khayrul.firebasechatapp.common.Resource
import com.khayrul.firebasechatapp.service.AuthService
import com.khayrul.firebasechatapp.ui.presentation.login.LoginEvent
import com.khayrul.firebasechatapp.utils.Utils
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(event: LoginEvent) = flow<Resource<Boolean>> {
        try {
            when(event) {
                is LoginEvent.Login -> {
                    val (email, pass) = event.user
                    if (Utils.validate(email, pass)) {
                        emit(Resource.Loading(true))
                        val res = authService.login(email, pass)
                        emit(Resource.Success(res, "Login is successful"))
                    } else {
                        emit(Resource.Error("Validation Failed"))
                    }
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Something went wrong"))
        }
    }
}