package com.a401.data.repository

import com.a401.data.datasource.remote.UserRemoteDataSource
import com.a401.data.model.request.LoginUserRequest
import com.a401.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {

    override suspend fun postIdToken(idToken: String, serviceType: String): Flow<String> {
        return userRemoteDataSource.postIdToken(idToken, serviceType)
    }

    override suspend fun postLoginInfo(userId: String, password: String): Flow<String> {
        return userRemoteDataSource.postLoginInfo(LoginUserRequest(userId, password))
    }

}