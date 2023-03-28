package com.example.firebasechatapp.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.firebasechatapp.model.model.Message
import com.example.firebasechatapp.service.AuthService
import com.example.firebasechatapp.model.repositories.RealtimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val realtimeRepository: RealtimeRepository,
    private val authService: AuthService,
    private val savedStateHandle: SavedStateHandle
) :
    BaseViewModel() {
    val txt: MutableStateFlow<String> = MutableStateFlow("")

    fun getAllMessages(uid2: String): Flow<List<Message>> {
        return realtimeRepository.getAllMessages(
            authService.getUid() ?: "", uid2
        )
    }

    fun sendMessage() {
        val uid2 = savedStateHandle.get<String>("id") ?: ""
        viewModelScope.launch {
            val user = authService.getCurrentUser()
            user?.let {
                val message = Message(name = user.name, message = txt.value)
                safeApiCall {
                    realtimeRepository.addMessage(
                        authService.getUid() ?: "",
                        uid2,
                        message
                    )
                }
                txt.value=""
            }
        }
    }
}


//listOf(
//Message("1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
//Message("2", "Sed et tortor eu nunc pharetra blandit ut vitae ligula."),
//Message("3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
//)