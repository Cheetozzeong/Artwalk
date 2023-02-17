package com.a401.domain.model

data class User(
    val userId: String,
    val nickName: String,
    val numOfRecord: Int,
    val numOfRoute: Int,
    val level: Int,
    val exp: Int
) {
    constructor(): this (
        "",
        "",
        0,
        0,
        0,
        0
    )
}
