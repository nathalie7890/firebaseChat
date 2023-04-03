package com.example.firebasechatapp.domain.useCase

import com.example.firebasechatapp.common.Resource
import com.example.firebasechatapp.service.AuthService
import com.example.firebasechatapp.ui.presentation.login.LoginEvent
import com.example.firebasechatapp.utils.Utils
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepo: AuthService
) {
    operator fun invoke(event: LoginEvent) = flow<Resource<Boolean>> {
        try {
            when (event) {
                is LoginEvent.Login -> {
                    val (id, name, email, pass) = event.user
                    if (Utils.validate(email, pass)) {
                        emit(Resource.Loading(true))
                        val res = authRepo.login(email, pass)
                        emit(Resource.Success(res, "Login successfully"))
                    } else {
                        emit(Resource.Error("Validation failed"))
                    }
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Something went wrong during login"))
        }
    }
}