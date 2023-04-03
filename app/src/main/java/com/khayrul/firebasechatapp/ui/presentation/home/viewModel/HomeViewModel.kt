package com.khayrul.firebasechatapp.ui.presentation.home.viewModel

import androidx.lifecycle.MutableLiveData
import com.khayrul.firebasechatapp.data.model.User

interface HomeViewModel {
    val users: MutableLiveData<List<User>>
}