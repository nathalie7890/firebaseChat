package com.khayrul.firebasechatapp.domain.usecase

import com.khayrul.firebasechatapp.common.Resource
import com.khayrul.firebasechatapp.data.model.User
import com.khayrul.firebasechatapp.domain.repository.UserRepository
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