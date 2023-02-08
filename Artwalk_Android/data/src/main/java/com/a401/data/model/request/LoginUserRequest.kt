package com.a401.data.model.request

data class LoginUserRequest(
    val userId: String,
    val password: String
) {
    constructor() : this(
        "",
        "",
    )
}