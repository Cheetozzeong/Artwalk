package com.a401.domain.usecase

import com.a401.domain.model.User
import com.a401.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfo @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<User> {
        return userRepository.getUserInfo()
    }
}