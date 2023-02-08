package com.a401.data.mapper

import com.a401.data.model.response.RouteListResponse
import com.a401.data.model.response.UserResponse
import com.a401.domain.model.User

fun userInfoFromUserAndRouteResponse(user: UserResponse, routes: RouteListResponse): User =
    User(
        userId = user.userId ?: "",
        nickName = user.nickname ?: "",
        numOfRecord = 0,
        numOfRoute = routes.data!!.count ?: 0,
        level = user.level ?: 0,
        exp = user.exp ?: 0
    )