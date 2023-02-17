package com.a401.domain.usecase

import com.a401.domain.model.User
import com.a401.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRegistArtWalk @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User, password: String): Flow<String> {
        return userRepository.postRegist(user, password)
    }
}