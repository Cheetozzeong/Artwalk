package com.a401.data.datasource.remote

import android.content.SharedPreferences
import com.a401.data.api.ApiClient
import com.a401.data.model.request.ArtWalkRegistRequest
import com.a401.data.model.response.UserResponse
import com.a401.domain.model.User
import com.a401.data.model.request.LoginUserRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import android.content.Context as Context

class UserRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : UserRemoteDataSource {

    private val a401UserApi = ApiClient.getUserServerApiService()
    private val prefs: SharedPreferences = context.getSharedPreferences("a401Token", Context.MODE_PRIVATE)
    private val accessToken: String = "Bearer ${prefs.getString("accessToken", "")}"

    override suspend fun postLogin(accessToken: String, refreshToken: String) {
        val responseHeader = a401UserApi.postLogin(accessToken, refreshToken).headers()
        val renewAccessToken = responseHeader["accessToken"]
        prefs.edit().putString("accessToken", renewAccessToken).apply()
        return
    }

    override suspend fun postIdToken(idToken: String, serviceType: String): Flow<String> {
        return flow{
            a401UserApi.postIdToken(idToken, serviceType).let{ response ->
                if(response.isSuccessful) {
                    val accessToken = response.headers()["accessToken"]
                    val refreshToken = response.headers()["refreshToken"]
                    prefs.edit().putString("accessToken", accessToken).apply()
                    prefs.edit().putString("refreshToken", refreshToken).apply()
                    emit("SUCCESS")
                }else {
                    emit("FAIL")
                }
            }
        }
    }

    override suspend fun postLoginInfo(loginInfo: LoginUserRequest): Flow<String> {
        return flow {
            a401UserApi.postLoginInfo(loginInfo).let { response ->
                if (response.isSuccessful) {
                    val accessToken = response.headers()["accessToken"]
                    val refreshToken = response.headers()["refreshToken"]
                    prefs.edit().putString("accessToken", accessToken).apply()
                    prefs.edit().putString("refreshToken", refreshToken).apply()
                    emit("SUCCESS")
                } else {
                    emit("FAIL")
                }
            }
        }
    }
    override suspend fun postRegist(user: User, password: String): Flow<UserResponse> {
        return flow {
            emit(
                a401UserApi.registArtWalk(
                    ArtWalkRegistRequest(
                        user.userId,
                        password,
                        user.nickName
                    )
                )
            )
        }
    }

    override suspend fun getUserInfo(): Flow<UserResponse> {
        return flow { emit(a401UserApi.getUserInfo(accessToken)) }
    }
}