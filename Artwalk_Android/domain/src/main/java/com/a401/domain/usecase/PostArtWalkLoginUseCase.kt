package com.a401.domain.usecase

import com.a401.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostArtWalkLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String, password: String): Flow<String> {
        return userRepository.postLoginInfo(
            userId = userId,
            password = password,
        )
    }
}