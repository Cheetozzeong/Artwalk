package com.a401.data.mapper

import com.a401.data.model.response.responseCode

fun processErrorcode(response: responseCode, refreshToken: String): Boolean {

    when(response.code){
        // 토큰 위조 된 경우
        "T002" -> return false

        // Refresh 토큰이 만료 된 경우
        "T003" -> return false

        // Access 토큰이 만료 된 경우
        "T004" ->
            if(refreshToken == null) {
                return false
            } else { // refreshToken 유효 기간이 7일 남은 경우
                return false
            }

        //  토큰의 형식이 JWT가 아닌 경우
        "T005" -> return false

        else -> return false
    }
}

