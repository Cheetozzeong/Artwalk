## Description
- 이 문서는 Artwalk API에 대한 내용을 기술하고 있습니다.
- 모든 요청은 header에 token을 포함하여 보내야 합니다.
- 모든 응답은 code와 data로 구성되어 있습니다.
  - code는 성공시 Ok, 실패시 Fail을 보냅니다.
  - data는 요청에 따른 응답 데이터가 포함되어 있습니다.


## API Lists
- [**1. Login Info API**](#1-user-access-api)
  - [어플리케이션 실행](#어플리케이션-실행)
  - [Artwalk 계정으로 회원가입](#Artwalk-계정으로-회원가입)
  - [Artwalk 계정으로 사용자 로그인](#Artwalk-계정으로-사용자-로그인)
  - [소셜 계정으로 사용자 로그인](#소셜-계정으로-사용자-로그인)
  - [모든 요청 시 헤더에 토큰 포함](#모든-요청-시-헤더에-토큰-포함)
  - [JWT 토큰 관련 예외 코드](#jwt-토큰-관련-예외-코드)
  - [T003 토큰 만료](#t003-토큰-만료)
  - [사용자 로그아웃](#사용자-로그아웃)
  - [사용자 탈퇴](#사용자-탈퇴)

- [**2. User Info API**](#2-user-info-api)
  - [사용자 기본 정보](#사용자-기본-정보)
  - [사용자 프로필 사진 조회](#사용자-프로필-사진-조회)
  - [사용자 정보 조회](#사용자-정보-조회)
  - [사용자 정보 검색](#사용자-정보-검색)

- [**3. Route API**](#3-route-api)
  - [경로 저장하기](#경로-저장하기)
  - [경로 불러오기](#경로-불러오기)
  - [경로 수정하기](#경로-수정하기)
  - [경로 삭제하기](#경로-삭제하기)
  - [경로 목록 불러오기](#경로-목록-불러오기)
  - [경로 썸네일 불러오기](#경로-썸네일-불러오기)

- [**4. Record API**](#4-record-api)
  - [기록 저장하기](#기록-저장하기)
  - [기록 불러오기](#기록-불러오기)
  - [기록 수정하기](#기록-수정하기)
  - [기록 삭제하기](#기록-삭제하기)
  - [기록 목록 불러오기](#기록-목록-불러오기)
  - [기록 썸네일 불러오기](#기록-썸네일-불러오기)
  - [공유이미지 불러오기](#공유이미지-불러오기)
  - [공유이미지 저장/수정하기](#공유이미지-저장수정하기)

- [**5. New API**](#5-new-api)
  - [양식 이름](#양식-이름)

<br>


## **1. User Access API**
<br>

  - 사용자 로그인 및 토큰 인증

<br>

### 어플리케이션 실행

    POST /auth/connect

- 어플리케이션 실행 시 토큰 갱신을 위해 accessToken과 refreshToken을 헤더에 담아 전송한다.
- request header
    ```json
    {
	  "accessToken": "Bearer ${accessToken}",
      "refreshToken": "Bearer ${refreshToken}"
    }
- response header
    ```json
    {
	  "accessToken": "${accessToken}",
      "refreshToken": "${refreshToken}"
    }
- response body
    ```json
    // 성공 시
    {
      "code": "Ok",
      "message": "SUCCESS"
    }
    // 실패 시
    {
      "code": "Fail",
      "message": "사용자 토큰 발급 실패"
    }
<br>

#
### Artwalk 계정으로 회원가입

    POST /auth/reg/artwalk

- Artwalk 계정으로 직접 가입하는 API입니다.
- request body
  - nickname: String
  - password: String
  - userId: String
    ```json
    {
        "nickname": "이싸피",
        "userId": "ssafy",
        "password": "password"
    }
- response body
    ```json
    // 성공 시
    {
      "code": "Ok",
      "message": "SUCCESS"
    }
    // 실패 시
    {
      "code": "Fail",
      "message": "사용자 아이디 중복"
    }
<br>

### Artwalk 계정으로 사용자 로그인

    POST /auth/login/artwalk

- Artwalk 계정으로 로그인을 수행하는 API 입니다.
- request body
    ```json
    {
        "userId": "ssafy",
        "password": "password"
    }
- response body
    ```json
    // 성공 시
    {
      "code": "Ok",
      "message": "SUCCESS"
    }
    // 실패 시
    {
      "code": "Fail",
      "message": "사용자 토큰 발급 실패"
    }
<br>

### 소셜 계정으로 사용자 로그인

    POST /auth/login/{serviceType}

- OpenID를 통해 외부 로그인을 수행하는 API입니다.
- 현재 서비스 종류는 'kakao'만 가능합니다.
- request header
  - id-token: String
    ```json
    {
	  "id-token": "${idToken}",
    }
- response header
  - accessToken : String
  - refreshToken : String
    ```json
    // 성공시
    {
      "accessToken": "${accessToken}",
      "refreshToken": "${refreshToken}"
    }
    // 실패 응답은 하단의 [JWT 토큰 관련 예외 코드 참조]
<br>

### 모든 요청 시 헤더에 토큰 포함

- 모든 요청에 대해 접근 헤더에 토큰을 포함합니다.
- request header
    ```json
    {
	  "accessToken": "Bearer ${accessToken}"
    }
<br>

#
### JWT 토큰 관련 예외 코드

- response body
  - message : String
  - code : String
    ```json
    // 토큰이 위조되었을 경우
    {
        "code": "T002",
        "message": "Token is Invalid"
    }
    // Refresh 토큰이 모두 만료되었을 경우
    {
        "code": "T003",
        "message": "Token is Expired"
    }
    // Access 토큰이 만료되었을 경우
    {
        "code": "T004",
        "message": "Access Token is Expired"
    }
    // 토큰의 형식이 JWT가 아닌 경우
    {
        "code": "T005",
        "message": "Token is not supported"
    }
<br>

#
### T003 토큰 만료
- 보유하고 있는 AccessToken과 RefreshToken을 요청 헤더에 포함해 전송합니다.
- API 주소는 현재 사용자가 접속을 시도하고 있는 주소로 요청합니다.
- request header
    - accessToken: String
    - refreshToken: String
    ```json
    {
	  "accessToken": "Bearer ${accessToken}",
      "refreshToken": "Bearer ${refreshToken}"
    }
- response Header
    ```json
    {
        "accessToken": "${accessToken}",
        "refreshToken": "${refreshToken}"
    }
<br>

#
### 사용자 로그아웃

    GET /auth/logout

- 현재 계정에서 로그아웃합니다.
- response Body
    ```json
    // 성공 시
    {
      "code": "Ok",
      "message": "SUCCESS"
    }
    // 실패 시
    {
      "code": "Fail",
      "message": "사용자 정보 확인 실패"
    }
<br>

#
### 사용자 탈퇴

    DELETE /auth/remove

- 현재 계정에서 탈퇴합니다.
- response Body (String)
    ```json
    // 성공 시
    {
      "code": "Ok",
      "message": "SUCCESS"
    }
    // 실패 시
    {
      "code": "Fail",
      "message": "사용자 정보 확인 실패"
    }
<br>


## **2. User Info API**
<br>

  - 사용자 정보를 요청합니다.

<br>

### 사용자 기본 정보

    GET /user/info

- 사용자 기본 정보 요청 API 입니다.
- response body(application/json)
  - code : String
  - user : Object
  - `response body 예시`
    ```json
    // 성공시
    {
        "code": "Ok",
        "user": {
            "exp": 151,
            "level": 7,
            "nickname": "이싸피",
            "password": "password",
            "profile": "/info/profile?userId=ssafy@ssafy.com",
            "recentAccess": "2023-02-10T07:34:29.535Z",
            "refreshToken": "token",
            "regDate": "2023-02-10T07:34:29.535Z",
            "userAuthority": "ROLE_USER",
            "userId": "ssafy@ssafy.com"
        }
    }
    // 실패시
    {
      "code": "Fail",
      "user": null
    }
<br>

### 사용자 프로필 사진 조회

    GET /user/info/profile

- 사용자 프로필 사진 요청 API 입니다.
- request body
    ```json
    {
        "userId": "ssafy"
    }
- response body(image/png)
  - png image

<br>

### 사용자 정보 조회

    GET /user/{userId}

- 특정 사용자의 정보를 조회하는 API 입니다.
- response body(application/json)
  - code : String
  - user : Object
  - `response body 예시`
    ```json
    // 성공시
    {
        "code": "Ok",
        "user": {
            "exp": 151,
            "level": 7,
            "nickname": "이싸피",
            "password": "password",
            "profile": "/info/profile?userId=ssafy@ssafy.com",
            "recentAccess": "2023-02-10T07:34:29.535Z",
            "refreshToken": "token",
            "regDate": "2023-02-10T07:34:29.535Z",
            "userAuthority": "ROLE_USER",
            "userId": "ssafy@ssafy.com"
        }
    }
    // 실패시
    {
      "code": "Fail",
      "user": null
    }
<br>

### 사용자 정보 검색

    GET /user/search?type=userId&keyword=ssafy

- 아이디와 닉네임을 이용해 특정 사용자의 정보를 조회하는 API 입니다.
- request parameter
    ```json
    // 사용자 아이디로 검색
    {
        "type": "userId",
        "keyword": "ssafy"
    }
    // 사용자 닉네임으로 검색
    {
        "type": "nickname",
        "keyword": "ssafy"
    }
- response body(application/json)
  - code : String
  - user : Object
  - `response body 예시`
    ```json
    // 성공시
    {
        "code": "Ok",
        "users": [
            "user": {
                "exp": 151,
                "level": 7,
                "nickname": "이싸피",
                "password": "password",
                "profile": "/info/profile?userId=ssafy@ssafy.com",
                "recentAccess": "2023-02-10T07:34:29.535Z",
                "refreshToken": "token",
                "regDate": "2023-02-10T07:34:29.535Z",
                "userAuthority": "ROLE_USER",
                "userId": "ssafy@ssafy.com"
            }
        ]
    }
    // 실패시
    {
      "code": "Fail",
      "user": null
    }
<br>

## **3. Route API**
<br>

### 경로 저장하기

    POST /route

- 경로를 저장하는 API입니다.
- request body(application/json)
  - duration: double
  - distance: double
  - title: String
  - geometry: String
  - `request body 예시`
    ```json
    {
	  "duration": 2904.2,
	  "distance": 354.24,
	  "title": "나의 나이스한 경로",
	  "geometry": "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f"
    }

- response body(application/json)
  - code : String
  - route : Route (저장된 경로 데이터)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "route": {
        "routeId": 6,
        "userId": "ssafy@ssafy.com",
        "maker": "ssafy@ssafy.com",
        "duration": 2904.2,
        "distance": 354.24,
        "geometry": "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f",
        "creation": "2023-01-25T03:34:49.000+00:00",
        "thumbnail": "/route/thumb/6",
        "title": "나의 나이스한 경로"
        }
    }

    // 실패시
    {
      "code": "Fail",
      "route": null
    }

#

### 경로 불러오기

    GET /route/{routeId}

- routeId를 통해 해당하는 경로 정보를 불러오는 API입니다.
- response body(application/json)
  - code : String
  - route : {
          
      - routeId: int
      - userId: String
      - maker: String
      - duration: double
      - distance: double
      - geometry: String
      - creation: Date
      - thumbnail: String
      - title: String

      }
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "route": {
        "routeId": 6,
        "userId": "ssafy@ssafy.com",
        "maker": "ssafy@ssafy.com",
        "duration": 2904.2,
        "distance": 354.24,
        "geometry": "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f",
        "creation": "2023-01-25T03:34:49.000+00:00",
        "thumbnail": "/route/thumb/6",
        "title": "나의 나이스한 경로"
        }
    }

    // 실패시
    {
      "code": "Fail",
      "route": null
    }

#

### 경로 수정하기

    PUT /route/{routeId}

- routeId를 통해 해당하는 경로 정보를 수정하는 API입니다.
- request body(application/json)
  - duration: double
  - distance: double
  - title: String
  - geometry: String
  - `request body 예시`
    ```json
    {
	  "duration": 123.2,
	  "distance": 12.24,
	  "title": "나의 업데이트한 경로",
	  "geometry": "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf"
    }

- response body(application/json)
  - code : String
  - route : Route (수정된 경로 데이터)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "route": {
        "routeId": 6,
        "userId": "ssafy@ssafy.com",
        "maker": "ssafy@ssafy.com",
        "duration": 123.2,
        "distance": 12.24,
        "geometry": "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf",
        "creation": "2023-01-25T03:34:49.000+00:00",
        "thumbnail": "/route/thumb/6",
        "title": "나의 업데이트한 경로"
        }
    }

    // 실패시
    {
      "code": "Fail",
      "route": null
    }

#

### 경로 삭제하기

    DELETE /route/{routeId}

- routeId를 통해 해당하는 경로를 삭제하는 API입니다.
- response body(application/json)
  - code : String
  - count : int (routeId와 일치하는 경로 개수)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "count": 0
    }

    // 실패시
    {
      "code": "Fail",
      "count": 1
    }

#

### 경로 목록 불러오기

    GET /route/list?user={boolean}

- 등록된 모든 경로 목록을 불러오는 API입니다.
- user가 true면 특정 사용자의 경로 목록을, false면 모든 사용자의 경로 목록을 불러옵니다. 
- query string
  - user: boolean
  - `query string 예시`
    ```plain text
    모든 사용자 경로 목록 - https://{domain}/route/list?user=false
    특정 사용자 경로 목록 - https://{domain}/route/list?user=true

- response body(application/json)
  - code : String
  - routes : Array of Route
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "routes": [   
        {
          "routeId": 5,
          "userId": "lee@ssafy.com",
          "maker": "lee@ssafy.com",
          "duration": 53.52,
          "distance": 2.31,
          "geometry": "%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f",
          "creation": "2023-01-24T02:15:21.000+00:00",
          "thumbnail": "/route/thumb/5",
          "title": "나의 경로"
        },
        {
          "routeId": 6,
          "userId": "ssafy@ssafy.com",
          "maker": "ssafy@ssafy.com",
          "duration": 123.2,
          "distance": 12.24,
          "geometry": "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf",
          "creation": "2023-01-25T03:34:49.000+00:00",
          "thumbnail": "/route/thumb/6",
          "title": "나의 업데이트한 경로"
        }
      ]

    }

    // 실패시
    {
      "code": "Fail",
      "routes": null
    }

#

### 경로 썸네일 불러오기

    GET /route/thumb/{routeId}

- routeId와 일치하는 경로의 썸네일 이미지를 불러오는 API입니다.
- response body(image/png)
  - png image


#


## **4. Record API**
<br>

### 기록 저장하기

    POST /record

- 기록을 저장하는 API입니다.
- request body(application/json)
  - duration: double
  - distance: double
  - detail: String
  - geometry: String
  - `request body 예시`
    ```json
    {
	  "duration": 2904.2,
	  "distance": 354.24,
	  "detail": "오운완~^^",
	  "geometry": "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f"
    }

- response body(application/json)
  - code : String
  - record : Record (저장된 기록 데이터)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "record": {
        "recordId": 6,
        "userId": "ssafy@ssafy.com",
        "duration": 2904.2,
        "distance": 354.24,
        "geometry": "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f",
        "creation": "2023-01-25T03:34:49.000+00:00",
        "thumbnail": "/record/thumb/6",
        "recentImage": "/record/image/6",
        "detail": "오운완~^^"
        }
    }

    // 실패시
    {
      "code": "Fail",
      "record": null
    }

#

### 기록 불러오기

    GET /record/{recordId}

- recordId를 통해 해당하는 기록 정보를 불러오는 API입니다.
- response body(application/json)
  - code : String
  - record : {
          
      - recordId: int
      - userId: String
      - duration: double
      - distance: double
      - geometry: String
      - creation: Date
      - thumbnail: String
      - recentImage: String
      - detail: String

      }
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "record": {
        "recordId": 6,
        "userId": "ssafy@ssafy.com",
        "duration": 2904.2,
        "distance": 354.24,
        "geometry": "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f",
        "creation": "2023-01-25T03:34:49.000+00:00",
        "thumbnail": "/record/thumb/6",
        "recentImage": "/record/image/6",
        "detail": "오운완~^^"
        }
    }

    // 실패시
    {
      "code": "Fail",
      "record": null
    }

#

### 기록 수정하기

    PUT /record/{recordId}

- recordId를 통해 해당하는 기록 정보를 수정하는 API입니다.
- request body(application/json)
  - detail: String
  - `request body 예시`
    ```json
    {
	  "detail": "내가 그린 기린"
    }

- response body(application/json)
  - code : String
  - record : Record (수정된 기록 데이터)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "record": {
        "recordId": 6,
        "userId": "ssafy@ssafy.com",
        "duration": 2904.2,
        "distance": 354.24,
        "geometry": "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f",
        "creation": "2023-01-25T03:34:49.000+00:00",
        "thumbnail": "/record/thumb/6",
        "recentImage": "/record/image/6",
        "detail": "내가 그린 기린"
        }
    }

    // 실패시
    {
      "code": "Fail",
      "record": null
    }

#

### 기록 삭제하기

    DELETE /record/{recordId}

- recordId를 통해 해당하는 기록을 삭제하는 API입니다.
- response body(application/json)
  - code : String
  - count : int (recordId와 일치하는 경로 개수)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "count": 0
    }

    // 실패시
    {
      "code": "Fail",
      "count": 1
    }

#

### 기록 목록 불러오기

    GET /record/list?user={boolean}

- 등록된 모든 기록 목록을 불러오는 API입니다.
- user가 true면 특정 사용자의 기록 목록을, false면 모든 사용자의 기록 목록을 불러옵니다. 
- query string
  - user: boolean
  - `query string 예시`
    ```plain text
    모든 사용자 기록 목록 - https://{domain}/record/list?user=false
    특정 사용자 기록 목록 - https://{domain}/record/list?user=true

- response body(application/json)
  - code : String
  - records : Array of Record
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "records": [   
        {
          "recordId": 5,
          "userId": "lee@google.com",
          "duration": 124.3,
          "distance": 24.14,
          "geometry": "%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f",
          "creation": "2023-01-23T02:14:24.000+00:00",
          "thumbnail": "/record/thumb/5",
          "recentImage": "/record/image/5",
          "detail": "내가 기린 그린"
        },
        {
          "recordId": 6,
          "userId": "ssafy@ssafy.com",
          "duration": 2904.2,
          "distance": 354.24,
          "geometry": "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f",
          "creation": "2023-01-25T03:34:49.000+00:00",
          "thumbnail": "/record/thumb/6",
          "recentImage": "/record/image/6",
          "detail": "오운완~^^"
        },
      ]

    }

    // 실패시
    {
      "code": "Fail",
      "records": null
    }

#

### 기록 썸네일 불러오기

    GET /record/thumb/{recordId}

- recordId와 일치하는 기록의 썸네일 이미지를 불러오는 API입니다.
- response body(image/png)
  - png image


#

### 공유이미지 불러오기

    GET /record/image/{link}

- link에 해당하는 공유이미지를 불러오는 API입니다.
- response body(image/png)
  - png image


#

### 공유이미지 저장/수정하기

    POST /record/image

- recordId를 통해 해당하는 기록의 공유 이미지를 생성/저장/수정하는 API입니다.
- request header
    - accessToken: String
    - editLink: String
    ```json
    {
	  "accessToken": "Bearer ${accessToken}",
      "editLink": "${editLink}"
    }
- request body(application/json)
  - polyLineWidth: int
  - polyLineColor: String
  - centerLon: double
  - centerLat: double
  - zoom: double
  - bearing: double
  - `request body 예시`
    ```json
    {
      "polyLineWidth": 6,
      "polyLineColor": "7505e6",
      "centerLon": 126.6069,
      "centerLat": 37.2446,
      "zoom": 12,
      "bearing": 90,
    }

- response body(application/json)
  - code : String
  - message : String (이미지가 반영된 기록 데이터)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "message": "zw7Ob19m"
    }

    // 실패시
    {
      "code": "Fail",
      "message": null
    }
<br>

#



## **5. New API**

<br>

### 양식 이름

    메서드 /경로

- OOO OOOO OOOOO OOOO API입니다.
- request body(application/json)
  - name: type
  - name: type
  - `request body 예시`
    ```json
    {
	  "key": "value",
	  "key": "value"
    }

- response body(application/json)
  - code : String
  - data : Object
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "data": { ... }
    }
    // 실패시
    {
      "code": "Fail",
      "data": null
    }

<br>

#