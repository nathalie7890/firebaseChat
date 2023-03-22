package com.example.firebasechatapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.firebasechatapp.data.model.Message
import com.example.firebasechatapp.repositories.RealtimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val realtimeRepository: RealtimeRepository
) :
    BaseViewModel() {

    val messages: MutableLiveData<List<Message>> = MutableLiveData(
        listOf(
            Message("1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
            Message("2", "Sed et tortor eu nunc pharetra blandit ut vitae ligula."),
            Message("3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
        )
    )


}