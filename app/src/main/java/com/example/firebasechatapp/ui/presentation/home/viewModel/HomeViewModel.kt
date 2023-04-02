package com.example.firebasechatapp.ui.presentation.home.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.firebasechatapp.data.model.User

interface HomeViewModel {
    val users: MutableLiveData<List<User>>
}