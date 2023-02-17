package com.a401.data.repository

import com.a401.data.datasource.remote.RecordRemoteDataSource
import com.a401.data.datasource.remote.RouteRemoteDataSource
import com.a401.data.datasource.remote.UserRemoteDataSource
import com.a401.data.mapper.userInfoFromUserAndRouteResponse
import com.a401.data.model.request.LoginUserRequest
import com.a401.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import com.a401.domain.model.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val routeRemoteDataSource: RouteRemoteDataSource,
    private val recordRemoteDataSource: RecordRemoteDataSource

): UserRepository {

    override suspend fun postIdToken(idToken: String, serviceType: String): Flow<String> {
        return userRemoteDataSource.postIdToken(idToken, serviceType)
    }

    override suspend fun postLoginInfo(userId: String, password: String): Flow<String> {
        return userRemoteDataSource.postLoginInfo(LoginUserRequest(userId, password))
    }

    override suspend fun postLogin(accessToken: String, refreshToken: String): Flow<String> {
        return userRemoteDataSource.postLogin(accessToken, refreshToken)
    }

    override suspend fun postRegist(user: User, password: String): Flow<String> {
        return flow {
            userRemoteDataSource.postRegist(user, password).collect { response ->
                if (response.code == "Ok") {
                    emit("SUCCESS")
                } else {
                    emit("FAIL")
                }
            }
        }
    }

    override suspend fun getUserInfo(): Flow<User> {
        return flow {
            userRemoteDataSource.getUserInfo().collect() { resultUser ->
                routeRemoteDataSource.getRouteCount().collect() { resultRoute ->
                    recordRemoteDataSource.getRecordCount().collect() { resultRecord ->
                        if (resultUser.code == "Ok" && resultRoute.code == "Ok" && resultRecord.code == "Ok") {
                            emit(
                                userInfoFromUserAndRouteResponse(
                                    resultUser,
                                    resultRoute,
                                    resultRecord
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    override suspend fun removeUser(): Flow<String> {
        return flow {
            userRemoteDataSource.postRemoveUser().collect() { result ->
                if(result.code == "Ok") {
                    emit("SUCCESS")
                }else {
                    emit("FAIL")
                }
            }
        }
    }
}