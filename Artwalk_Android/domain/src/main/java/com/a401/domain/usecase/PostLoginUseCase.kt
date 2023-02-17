package com.a401.domain.usecase

import com.a401.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String): Flow<String> {
        return userRepository.postLogin(accessToken, refreshToken)
    }
}