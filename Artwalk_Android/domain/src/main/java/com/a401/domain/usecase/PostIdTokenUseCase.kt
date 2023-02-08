package com.a401.domain.usecase

import com.a401.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostIdTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(idToken: String): Flow<String> {
        return userRepository.postIdToken(idToken)
    }
}