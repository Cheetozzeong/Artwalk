package com.a401.data.model.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class UserResponse(
    @SerializedName("message") var message: String?,
    @SerializedName("code") var code: String?
)

data class responseCode(
    var code: String?
)