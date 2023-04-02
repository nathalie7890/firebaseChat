package com.example.firebasechatapp.domain.usecase

import com.example.firebasechatapp.common.Resource
import com.example.firebasechatapp.service.AuthService
import com.example.firebasechatapp.ui.presentation.register.SignupEvent
import com.example.firebasechatapp.utils.Utils
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(event: SignupEvent) = flow<Resource<Boolean>> {
        try {
            when (event) {
                is SignupEvent.signup -> {
                    val (id, name, email, pass) = event.user

                    if (Utils.validate(name, email, pass)) {
                        emit(Resource.Loading(true))
                        val res = authService.register(event.user)
                        emit(Resource.Success(res, "Signup is successful"))
                    } else {
                        emit(Resource.Error("Validation Failed"))
                    }
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Something went wrong!"))
        }
    }
}