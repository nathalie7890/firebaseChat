package com.example.firebasechatapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.firebasechatapp.data.model.Chat
import com.example.firebasechatapp.data.model.Message
import com.example.firebasechatapp.data.model.User
import com.example.firebasechatapp.data.service.AuthService
import com.example.firebasechatapp.repositories.RealtimeRepository
import com.example.firebasechatapp.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authService: AuthService
) : BaseViewModel() {

    val users: MutableLiveData<List<User>> = MutableLiveData()

    override fun onViewCreated() {
        super.onViewCreated()
        viewModelScope.launch {
            val res = safeApiCall { userRepository.getUsers() }
            res?.let {
                users.value = it.filter { user -> authService.getUid() != user.id }
            }
        }
    }
}
