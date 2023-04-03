package com.khayrul.firebasechatapp.ui.presentation.message.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.khayrul.firebasechatapp.common.Resource
import com.khayrul.firebasechatapp.data.model.Message
import com.khayrul.firebasechatapp.service.AuthService
import com.khayrul.firebasechatapp.domain.usecase.MessageUseCase
import com.khayrul.firebasechatapp.ui.presentation.base.viewModel.BaseViewModel
import com.khayrul.firebasechatapp.ui.presentation.message.MessageEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val messageUseCase: MessageUseCase,
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
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
                uid2
            )
        ).onEach {
            when(it) {
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
                messageUseCase(MessageEvent.SendMessage(
                    authService.getUid() ?: "",
                    uid2,
                    message
                )).onEach {
                    when(it) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {

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