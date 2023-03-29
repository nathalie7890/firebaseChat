package com.khayrul.firebasechatapp.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.khayrul.firebasechatapp.model.model.Message
import com.khayrul.firebasechatapp.service.AuthService
import com.khayrul.firebasechatapp.model.repositories.RealtimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val realtimeRepository: RealtimeRepository,
    private val authService: AuthService,
    private val savedStateHandle: SavedStateHandle
) :
    BaseViewModel() {
    val txt: MutableStateFlow<String> = MutableStateFlow("")
    private val uid2 = savedStateHandle.get<String>("id") ?: ""

    fun getAllMessages(uid2: String): Flow<List<Message>> {
        return realtimeRepository.getAllMessages(
            authService.getUid() ?: "", uid2
        )
    }

    fun sendMessage() {
        viewModelScope.launch {
            val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH)
            val date = formatter.format(Date())
            val user = authService.getCurrentUser()
            user?.let {
                val message = Message(name = user.name, message = txt.value, dateTime = date)
                safeApiCall {
                    realtimeRepository.addMessage(
                        authService.getUid() ?: "",
                        uid2,
                        message
                    )
                }
                txt.value = ""
            }
        }
    }
}