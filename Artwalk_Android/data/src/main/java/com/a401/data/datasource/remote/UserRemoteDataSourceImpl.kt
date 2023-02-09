package com.a401.data.datasource.remote

import android.content.SharedPreferences
import com.a401.data.api.ApiClient
import com.a401.data.utils.getErrorCode
import com.a401.data.model.request.ArtWalkRegistRequest
import com.a401.domain.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import android.content.Context as Context

class UserRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : UserRemoteDataSource {

    private val a401UserApi = ApiClient.getUserServerApiService()
    private val prefs: SharedPreferences = context.getSharedPreferences("a401Token", Context.MODE_PRIVATE)

    override suspend fun postIdToken(idToken: String) {
        val responseHeader = a401UserApi.postIdToken(idToken, "kakao").headers()
        val accessToken = responseHeader["accessToken"]
        val refreshToken = responseHeader["refreshToken"]
        prefs.edit().putString("accessToken", accessToken).apply()
        prefs.edit().putString("refreshToken", refreshToken).apply()
        return
    }

    override suspend fun postLogin(accessToken: String, refreshToken: String) {
        val responseHeader = a401UserApi.postLogin(accessToken, refreshToken).headers()
        val renewAccessToken = responseHeader["accessToken"]
        val errorCode = a401UserApi.postLogin(accessToken, refreshToken).body()?.code
        if (errorCode != null) {
            getErrorCode(errorCode) // ErrorMessage가 retu+
        }

        prefs.edit().putString("accessToken", renewAccessToken).apply()
        return
    }

    override suspend fun postRegist(user: User, password: String): Flow<Response<Void>> {
        return  flow {
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
}