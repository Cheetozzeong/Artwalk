package com.a401.domain.usecase

import com.a401.domain.repository.UserRepository
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String) {
        return userRepository.postLogin(accessToken, refreshToken)
    }
}