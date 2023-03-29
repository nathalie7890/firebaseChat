package com.khayrul.firebasechatapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.khayrul.firebasechatapp.model.model.User
import com.khayrul.firebasechatapp.service.AuthService
import com.khayrul.firebasechatapp.model.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authService: AuthService
) : BaseViewModel() {

    val users: MutableLiveData<List<User>> = MutableLiveData()


    override fun onViewCreated() {
        super.onViewCreated()
        viewModelScope.launch {
            val res = safeApiCall { userRepository.getUsers() }
            res?.let {
                 users.value = it.filter {user -> authService.getUid() != user.id}
            }
        }
    }
}