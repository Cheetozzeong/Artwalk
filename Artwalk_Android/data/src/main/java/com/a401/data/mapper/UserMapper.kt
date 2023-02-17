package com.a401.data.mapper

import com.a401.data.model.response.RecordListResponse
import com.a401.data.model.response.RouteListResponse
import com.a401.data.model.response.UserResponse
import com.a401.domain.model.User

fun userInfoFromUserAndRouteResponse(
    userResponse: UserResponse,
    routes: RouteListResponse,
    records: RecordListResponse

): User =
    User(
        userId = userResponse.user.userId ?: "",
        nickName = userResponse.user.nickname ?: "",
        numOfRecord = records.count?: 0,
        numOfRoute = routes.count ?: 0,
        level = userResponse.user.level ?: 0,
        exp = userResponse.user.exp ?: 0
    )