package com.example.firebasechatapp.ui.presentation.home.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.firebasechatapp.data.model.User
import com.example.firebasechatapp.service.AuthService
import com.example.firebasechatapp.domain.repository.UserRepository
import com.example.firebasechatapp.ui.presentation.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImplementation @Inject constructor(
    private val userRepository: UserRepository,
    private val authService: AuthService
) : HomeViewModel, BaseViewModel() {

    override val users: MutableLiveData<List<User>> = MutableLiveData()

    override fun onViewCreated() {
        super.onViewCreated()
        viewModelScope.launch {
            val res = safeApiCall { userRepository.getUsers() }
            res?.let {
                users.value = it.filter { user -> authService.getUid() != user.id }
            }
        }
    }
}
