package com.khayrul.firebasechatapp.ui.viewModels.auth

import androidx.lifecycle.viewModelScope
import com.khayrul.firebasechatapp.data.model.User
import com.khayrul.firebasechatapp.data.service.AuthService
import com.khayrul.firebasechatapp.ui.utils.Utils
import com.khayrul.firebasechatapp.ui.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepo: AuthService) : BaseViewModel() {
    val signupFinish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun signUp(name: String, email: String, password: String) {
        if (Utils.validate(name, email, password)) {
            viewModelScope.launch {
                safeApiCall {
                    authRepo.register(User(name = name, email = email, password = password))
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