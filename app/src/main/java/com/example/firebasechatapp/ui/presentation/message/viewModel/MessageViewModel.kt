package com.example.firebasechatapp.ui.presentation.message.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.firebasechatapp.common.Resource
import com.example.firebasechatapp.data.model.Message
import com.example.firebasechatapp.service.AuthService
import com.example.firebasechatapp.domain.repository.RealtimeRepository
import com.example.firebasechatapp.domain.usecase.MessageUseCase
import com.example.firebasechatapp.ui.presentation.base.viewModel.BaseViewModel
import com.example.firebasechatapp.ui.presentation.message.MessageEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val realtimeRepository: RealtimeRepository,
    private val authService: AuthService,
    private val savedStateHandle: SavedStateHandle,
    private val messageUseCase: MessageUseCase
) :
    BaseViewModel() {
    val txt: MutableStateFlow<String> = MutableStateFlow("")
    val messages: MutableStateFlow<List<Message>> = MutableStateFlow(listOf())
    private val uid2 = savedStateHandle.get<String>("id") ?: ""

    override fun onViewCreated() {
        super.onViewCreated()
        getAllMessages()
    }

    private fun getAllMessages() {
        messageUseCase(
            MessageEvent.GetMessages(
                authService.getUid() ?: "",
                uid2,
            )
        ).onEach {
            when (it) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    messages.value = it.data!!
                }

                is Resource.Error -> {
                    error.emit(it.message!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun sendMessage() {
        viewModelScope.launch {
            val user = authService.getCurrentUser()
            user?.let {
                val message = Message(name = user.name, message = txt.value)
                txt.value = ""

                messageUseCase(
                    MessageEvent.SendMessage(
                        authService.getUid() ?: "",
                        uid2,
                        message
                    )
                ).onEach {
                    when (it) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            messages.value = it.data!!
                        }

                        is Resource.Error -> {
                            error.emit(it.message!!)
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}


//listOf(
//Message("1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
//Message("2", "Sed et tortor eu nunc pharetra blandit ut vitae ligula."),
//Message("3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
//)