package com.example.firebasechatapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.firebasechatapp.repositories.RealtimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val realtimeRepository: RealtimeRepository
) : BaseViewModel() {

    override fun onViewCreated() {
        super.onViewCreated()
        viewModelScope.launch {
            realtimeRepository.getAllMessages().collect {
                Log.d("debugging", it.toString())
            }
        }
    }

    fun addMessage() {
        viewModelScope.launch {
            realtimeRepository.addMessage()
        }
    }
}