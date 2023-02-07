package com.a401.data.repository

import com.a401.data.datasource.remote.UserRemoteDataSource
import com.a401.domain.model.User
import com.a401.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {

    override suspend fun postIdToken(idToken: String) {
        return userRemoteDataSource.postIdToken(idToken)
    }

    override suspend fun postRegist(user: User, password: String): Flow<String> {
        return flow {
            userRemoteDataSource.postRegist(user, password).collect { response ->
                if(response.isSuccessful) {
                    emit("SUCCESS")
                }else {
                    emit("FAIL")
                }
            }
        }
    }
}