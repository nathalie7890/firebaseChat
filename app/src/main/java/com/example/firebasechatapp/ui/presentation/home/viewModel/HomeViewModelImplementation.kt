package com.example.firebasechatapp.ui.presentation.home.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.firebasechatapp.common.Resource
import com.example.firebasechatapp.data.model.User
import com.example.firebasechatapp.service.AuthService
import com.example.firebasechatapp.domain.repository.UserRepository
import com.example.firebasechatapp.domain.useCase.GetUsersUseCase
import com.example.firebasechatapp.ui.presentation.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImplementation @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val authService: AuthService
) : HomeViewModel, BaseViewModel() {

    override val users: MutableLiveData<List<User>> = MutableLiveData()

    override fun onViewCreated() {
        super.onViewCreated()
        getUsersUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    users.value = it.data?.filter { user -> authService.getUid() != user.id }
                }
                is Resource.Error -> {
                    error.emit(it.message!!)
                }
            }
        }.launchIn(viewModelScope)
    }
}
