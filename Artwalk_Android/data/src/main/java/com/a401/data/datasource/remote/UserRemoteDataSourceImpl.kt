package com.a401.data.datasource.remote

import android.content.Context
import android.content.SharedPreferences
import com.a401.data.api.ApiClient
import com.a401.data.model.response.UserResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

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

    override suspend fun postLogin(accessToken: String, refreshToken: String) : Boolean {
        val responseHeader = a401UserApi.postLogin(accessToken, refreshToken).headers()
        val renewAccessToken = responseHeader["accessToken"]
        val refreshTokenForCheck = responseHeader["refreshToken"]
        prefs.edit().putString("accessToken", renewAccessToken).apply()

        val response = UserResponse(accessToken,refreshTokenForCheck)
        when(response.code){
            // 토큰 위조 된 경우
            "T002" -> return false

            // Refresh 토큰이 만료 된 경우
            "T003" -> return false

            // Access 토큰이 만료 된 경우
            "T004" ->
                if(refreshTokenForCheck == null) {
                    return false
                } else { // refreshToken 유효 기간이 7일 남은 경우
                    return false
                }

            //  토큰의 형식이 JWT가 아닌 경우
            "T005" -> return false

            else -> return false
        }
    }

}