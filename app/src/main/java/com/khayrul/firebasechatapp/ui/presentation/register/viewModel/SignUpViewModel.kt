package com.khayrul.firebasechatapp.ui.presentation.register.viewModel

import androidx.lifecycle.viewModelScope
import com.khayrul.firebasechatapp.data.model.User
import com.khayrul.firebasechatapp.service.AuthService
import com.khayrul.firebasechatapp.ui.presentation.base.viewModel.BaseViewModel
import com.khayrul.firebasechatapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepo: AuthService) : BaseViewModel() {
    val signupFinish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val name: MutableStateFlow<String> = MutableStateFlow("")
    val email: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = MutableStateFlow("")

    fun signUp() {
        if (Utils.validate(name.value, email.value, password.value)) {
            viewModelScope.launch {
                safeApiCall {
                    authRepo.register(User(name = name.value, email = email.value, password = password.value))
                    signupFinish.emit(Unit)
                }
            }
        } else {
            viewModelScope.launch {
                error.emit("Failed to Register, Please fill in all information")
            }
        }
    }
}