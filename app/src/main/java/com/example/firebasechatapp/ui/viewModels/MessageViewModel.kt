package com.example.firebasechatapp.ui.viewModels

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

    fun getAllMessages(): Flow<List<Message>> {
        return realtimeRepository.getAllMessages()
    }

    fun sendMessage(msg: String) {
        viewModelScope.launch {
            val message = Message(name = authService.getCurrentUser()?.name ?: "")
            safeApiCall {
                realtimeRepository.addMessage(msg)
            }
        }
    }
}
//listOf(
//Message("1", "", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
//Message("2", "", "Sed et tortor eu nunc pharetra blandit ut vitae ligula."),
//Message("3", "", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
//)