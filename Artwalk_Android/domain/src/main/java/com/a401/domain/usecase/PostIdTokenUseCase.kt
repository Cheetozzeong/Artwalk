package com.a401.domain.usecase

import com.a401.domain.repository.UserRepository
import javax.inject.Inject

class PostIdTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(idToken: String) {
        return userRepository.postIdToken(idToken)
    }
}