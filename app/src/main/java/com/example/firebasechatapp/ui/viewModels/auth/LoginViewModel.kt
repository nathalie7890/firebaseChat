package com.example.firebasechatapp.ui.viewModels.auth

import androidx.lifecycle.viewModelScope
import com.example.firebasechatapp.data.service.AuthService
import com.example.firebasechatapp.ui.utils.Utils
import com.example.firebasechatapp.ui.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepo: AuthService) : BaseViewModel() {
    val loginFinish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun login(email: String, password: String) {
        if (Utils.validate(email, password)) {
            viewModelScope.launch {
                safeApiCall {
                    authRepo.login(email, password)
                    loginFinish.emit(Unit)
                }
            }
        } else {
            viewModelScope.launch {
                error.emit("Failed to Login, Please try again")
            }
        }
    }
}