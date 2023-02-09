package com.a401.data.utils

import com.a401.data.model.response.responseCode

fun getErrorCode(response: responseCode): String {
    when(response.code){
        // 토큰 위조 된 경우
        "T002" ->  return "FAIL: 잘못된 접근입니다"

        // Refresh 토큰이 만료 된 경우
        "T003" ->  return "FAIL: 다시 로그인 해주세요!"

        // Access 토큰이 만료 된 경우
        "T004" ->  return "FAIL: 토큰이 만료 되었습니다."

        //  토큰의 형식이 JWT가 아닌 경우
        "T005" ->  return "FAIL: 잘못된 접근입니다"
    }
    return "SUCCESS"
}

