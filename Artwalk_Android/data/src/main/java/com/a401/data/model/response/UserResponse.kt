package com.a401.data.model.response

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class UserResponse(
    @SerializedName("exp") var exp: Int?,
    @SerializedName("level") var level: Int?,
    @SerializedName("nickname") var nickname: String?,
    @SerializedName("password") var password: String?,
    @SerializedName("profile") var profile: String?,
    @SerializedName("regDate") var regDate: String?,
    @SerializedName("userId") var userId: String?,
)