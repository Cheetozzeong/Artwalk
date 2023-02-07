package com.a401.data.repository

import com.a401.data.datasource.remote.UserRemoteDataSource
import com.a401.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {
    override suspend fun postIdToken(idToken: String) {
        return userRemoteDataSource.postIdToken(idToken)
    }

    override suspend fun postLogin(accessToken: String, refreshToken: String) {
        return userRemoteDataSource.postLogin(accessToken, refreshToken)
    }
}