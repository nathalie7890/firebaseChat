package com.example.firebasechatapp.domain.usecase

import com.example.firebasechatapp.common.Resource
import com.example.firebasechatapp.data.model.User
import com.example.firebasechatapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        try {
            emit(Resource.Loading())
            val res = userRepository.getUsers()
            emit(Resource.Success(res))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: ""))
        }
    }
}