package com.khayrul.firebasechatapp.ui.viewModels

import androidx.lifecycle.viewModelScope
import com.khayrul.firebasechatapp.data.model.Message
import com.khayrul.firebasechatapp.data.service.AuthService
import com.khayrul.firebasechatapp.repositories.RealtimeRepository
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
        return realtimeRepository.getAllMessages(
            authService.getUid() ?: "", "ziTDRcWgMhZyNogImTJRSR5Bhch1"
        )
    }

    fun sendMessage(msg: String) {
        viewModelScope.launch {
            val user = authService.getCurrentUser()
            user?.let {
                val message = Message(name = user.name, message = msg)
                safeApiCall {
                    realtimeRepository.addMessage(
                        authService.getUid() ?: "",
                        "ziTDRcWgMhZyNogImTJRSR5Bhch1",
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