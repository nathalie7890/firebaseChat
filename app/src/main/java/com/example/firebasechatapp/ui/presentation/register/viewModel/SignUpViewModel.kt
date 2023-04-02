package com.example.firebasechatapp.ui.presentation.register.viewModel

import androidx.lifecycle.viewModelScope
import com.example.firebasechatapp.common.Resource
import com.example.firebasechatapp.data.model.User
import com.example.firebasechatapp.domain.usecase.SignupUseCase
import com.example.firebasechatapp.service.AuthService
import com.example.firebasechatapp.ui.presentation.base.viewModel.BaseViewModel
import com.example.firebasechatapp.ui.presentation.register.SignupEvent
import com.example.firebasechatapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
//    private val authRepo: AuthService
    private val signupUseCase: SignupUseCase
) : BaseViewModel() {
    val signupFinish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val name: MutableStateFlow<String> = MutableStateFlow("")
    val email: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = MutableStateFlow("")

    fun signUp() {
        signupUseCase(
            SignupEvent.signup(
                User(name=name.value, email=email.value, password=password.value)
            )
        ).onEach {
            when (it) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    signupFinish.emit(Unit)
                }

                is Resource.Error -> {
                    error.emit(it.message!!)
                }
            }
        }.launchIn(viewModelScope)

//        if (Utils.validate(name.value, email.value, password.value)) {
//            viewModelScope.launch {
//                safeApiCall {
//                    authRepo.register(
//                        User(
//                            name = name.value,
//                            email = email.value,
//                            password = password.value
//                        )
//                    )
//                    signupFinish.emit(Unit)
//                }
//            }
//        } else {
//            viewModelScope.launch {
//                error.emit("Failed to Register, Please fill in all information")
//            }
//        }
    }
}