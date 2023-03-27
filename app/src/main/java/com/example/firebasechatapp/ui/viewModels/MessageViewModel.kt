package com.example.firebasechatapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.firebasechatapp.data.model.Message
import com.example.firebasechatapp.data.service.AuthService
import com.example.firebasechatapp.repositories.RealtimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val realtimeRepository: RealtimeRepository,
    private val authService: AuthService
) :
    BaseViewModel() {

    fun getAllMessages(uid2: String): Flow<List<Message>> {
        return realtimeRepository.getAllMessages(
            authService.getUid() ?: "", uid2
        )
    }

    fun sendMessage(uid2: String, msg: String) {
        viewModelScope.launch {
            val user = authService.getCurrentUser()
            user?.let {
                val message = Message(name = user.name, message = msg)
                safeApiCall {
                    realtimeRepository.addMessage(
                        authService.getUid() ?: "",
                        uid2,
                        message
                    )
                }

            }
        }
    }
}


//listOf(
//Message("1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
//Message("2", "Sed et tortor eu nunc pharetra blandit ut vitae ligula."),
//Message("3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
//)