## Description
- Artwalk API 문서 (작성자: 공예찬)
- 모든 응답은 code와 data로 구성되어 있습니다.
  - code는 성공시 Ok, 실패시 Fail을 보냅니다.
  - data는 요청에 따른 응답 데이터가 포함되어 있습니다.

#
## API Lists
  - [**Route API**](#1-route-api)
    - [경로 저장하기](#경로-저장하기) 
    - [경로 불러오기](#경로-불러오기) 
    - [경로 수정하기](#경로-수정하기) 
    - [경로 삭제하기](#경로-삭제하기) 
    - [전체 경로 목록 불러오기](#전체-경로-목록-불러오기) 
    - [사용자 경로 목록 불러오기](#사용자-경로-목록-불러오기) 
    - [경로 썸네일 불러오기](#경로-썸네일-불러오기) 
  - [**Record API**](#2-record-api)
    - [기록 저장하기](#기록-저장하기) 
    - [기록 불러오기](#기록-불러오기) 
    - [기록 수정하기](#기록-수정하기) 
    - [기록 삭제하기](#기록-삭제하기) 
    - [전체 기록 목록 불러오기](#전체-기록-목록-불러오기) 
    - [사용자 기록 목록 불러오기](#사용자-기록-목록-불러오기) 
    - [기록 썸네일 불러오기](#기록-썸네일-불러오기) 
    - [공유이미지 불러오기](#공유이미지-불러오기) 
    - [공유이미지 저장/수정하기](#공유이미지-저장수정하기) 
- [**Login API**](#1-route-api)
    - [회원가입 및 로그인](#경로-저장하기) 
    - [회원 정보 가져오기](#경로-불러오기) 
    - [회원 탈퇴](#경로-수정하기) 


#
## 1. Route API
<br>

### 경로 저장하기
<br>

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
  - data : Route (저장된 경로 데이터)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "data": {
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
      "data": null
    }

#

### 경로 불러오기
<br>

    GET /route/{routeId}

- routeId를 통해 해당하는 경로 정보를 불러오는 API입니다.
- response body(application/json)
  - code : String
  - data : {
          
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
      "data": {
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
      "data": null
    }

#

### 경로 수정하기
<br>

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
  - data : Route (수정된 경로 데이터)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "data": {
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
      "data": null
    }

#

### 경로 삭제하기
<br>

    DELETE /route/{routeId}

- routeId를 통해 해당하는 경로를 삭제하는 API입니다.
- response body(application/json)
  - code : String
  - data : int (routeId와 일치하는 경로 개수)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "data": 0
    }

    // 실패시
    {
      "code": "Fail",
      "data": 1
    }

#

### 전체 경로 목록 불러오기
<br>

    GET /route/list

- 등록된 모든 경로 정보를 불러오는 API입니다.
- response body(application/json)
  - code : String
  - data : Array of Route
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "data": [   
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
      "data": null
    }

#

### 사용자 경로 목록 불러오기
<br>

    GET /route/list/{userId}

- userId와 일치하는 사용자가 등록한 모든 경로 정보를 불러오는 API입니다.
- response body(application/json)
  - code : String
  - data : Array of Route
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "data": [   
        {
          "routeId": 4,
          "userId": "ssafy@ssafy.com",
          "maker": "ssafy@ssafy.com",
          "duration": 53.52,
          "distance": 2.31,
          "geometry": "%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f",
          "creation": "2023-01-22T02:15:21.000+00:00",
          "thumbnail": "/route/thumb/4",
          "title": "신기한 경로"
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
      "data": null
    }

#

### 경로 썸네일 불러오기
<br>

    GET /route/thumb/{routeId}

- routeId와 일치하는 경로의 썸네일 이미지를 불러오는 API입니다.
- response body(image/png)
  - png image
  - `response body 예시`

    ![Thumbnail Image](uploads/c589571d61100f16c6ff4508b069b1b6/1674618612489991872.png)

#

## 2. Record API
<br>

### 기록 저장하기
<br>

    POST /route

- 기록을 저장하는 API입니다.
- request body(application/json)
  - taken: double
  - distance: double
  - detail: String
  - geometry: String
  - `request body 예시`
    ```json
    {
	  "taken": 2904.2,
	  "distance": 354.24,
	  "detail": "오운완~^^",
	  "geometry": "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f"
    }

- response body(application/json)
  - code : String
  - data : Record (저장된 기록 데이터)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "data": {
        "recordId": 6,
        "userId": "ssafy@ssafy.com",
        "taken": 2904.2,
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
      "data": null
    }

#

### 기록 불러오기
<br>

    GET /record/{recordId}

- recordId를 통해 해당하는 기록 정보를 불러오는 API입니다.
- response body(application/json)
  - code : String
  - data : {
          
      - recordId: int
      - userId: String
      - taken: double
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
      "data": {
        "recordId": 6,
        "userId": "ssafy@ssafy.com",
        "taken": 2904.2,
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
      "data": null
    }

#

### 기록 수정하기
<br>

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
  - data : Record (수정된 기록 데이터)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "data": {
        "recordId": 6,
        "userId": "ssafy@ssafy.com",
        "taken": 2904.2,
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
      "data": null
    }

#

### 기록 삭제하기
<br>

    DELETE /record/{recordId}

- recordId를 통해 해당하는 기록을 삭제하는 API입니다.
- response body(application/json)
  - code : String
  - data : int (recordId와 일치하는 경로 개수)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "data": 0
    }

    // 실패시
    {
      "code": "Fail",
      "data": 1
    }

#

### 전체 기록 목록 불러오기
<br>

    GET /record/list

- 저장된 모든 기록 정보를 불러오는 API입니다.
- response body(application/json)
  - code : String
  - data : Array of Record
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "data": [   
        {
          "recordId": 5,
          "userId": "lee@google.com",
          "taken": 124.3,
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
          "taken": 2904.2,
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
      "data": null
    }

#

### 사용자 기록 목록 불러오기
<br>

    GET /record/list/{userId}

- userId와 일치하는 사용자가 저장한 모든 기록 정보를 불러오는 API입니다.
- response body(application/json)
  - code : String
  - data : Array of Record
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "data": [   
        {
          "recordId": 4,
          "userId": "ssafy@ssafy.com",
          "taken": 42.68,
          "distance": 1.45,
          "geometry": "%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f",
          "creation": "2023-01-22T14:53:21.000+00:00",
          "thumbnail": "/record/thumb/4",
          "recentImage": "/record/image/4",
          "detail": "날씨가 좋아서"
        },
        {
          "recordId": 6,
          "userId": "ssafy@ssafy.com",
          "taken": 2904.2,
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
      "data": null
    }

#

### 기록 썸네일 불러오기
<br>

    GET /record/thumb/{recordId}

- recordId와 일치하는 기록의 썸네일 이미지를 불러오는 API입니다.
- response body(image/png)
  - png image
  - `response body 예시`

    ![Thumbnail Image](uploads/c589571d61100f16c6ff4508b069b1b6/1674618612489991872.png)

#

### 공유이미지 불러오기
<br>

    GET /record/image/{recordId}

- recordId와 일치하는 기록의 공유이미지를 불러오는 API입니다.
- response body(image/png)
  - png image
  - `response body 예시`

    ![1674720974600332048](uploads/ba961b676a54df1b5bc6ded6f7886c19/1674720974600332048.png)

#

### 공유이미지 저장/수정하기
<br>

    POST /record/image/{recordId}

- recordId를 통해 해당하는 기록의 공유 이미지를 생성/저장/수정하는 API입니다.
- request body(application/json)
  - polyLineWidth: int
  - polyLineColor: String
  - geometry: String
  - minLon: double
  - minLat: double
  - maxLon: double
  - maxLat: double
  - `request body 예시`
    ```json
    {
      "polyLineWidth": 6,
      "polyLineColor": "7505e6",
      "geometry": "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f",
      "minLon": 126.6069,
      "minLat": 37.2446,
      "maxLon": 127.3181,
      "maxLat": 37.8086,
    }

- response body(application/json)
  - code : String
  - data : Record (이미지가 반영된 기록 데이터)
  - `response body 예시`
    ```json
    // 성공시
    {
      "code": "Ok",
      "data": {
        "recordId": 6,
        "userId": "ssafy@ssafy.com",
        "taken": 2904.2,
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
      "data": null
    }

#

## 3. Login API
<br>

  사용자 로그인 및 토큰 인증

<br>

### 어플리케이션 실행
<br>

어플리케이션 실행 시 새로운 AccessToken을 발급합니다.
<br>

    POST /login/{serviceType}

- response header
    ```json
    {
	  "accessToken": "Bearer ${accessToken}",
    }
### 회원가입 및 사용자 로그인
<br>

    POST /login/{serviceType}

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
  - refreshToken : Object
    ```json
    // 성공시
    {
      "accessToken": "${accessToken}",
      "refreshToken": "${refreshToken}"
    }
<br>

### JWT 토큰 관련 예외 코드
<br>

모든 요청에 대해 접근 헤더에 토큰을 포함합니다.

- request header
    ```json
    {
	  "accessToken": "Bearer ${accessToken}",
    }
- response body
  - message : String
  - code : String
    ```json
    // 토큰이 위조되었을 경우
    {
        "message": "Token is Invalid",
        "code": "T002"
    }
    // Refresh 토큰이 모두 만료되었을 경우
    {
        "message": "Token is Expired",
        "code": "T003"
    }
    // Access 토큰이 만료되었을 경우
    {
        "message": "Access Token is Expired",
        "code": "T004"
    }
    // 토큰의 형식이 JWT가 아닌 경우
    {
        "message": "Token is not supported",
        "code": "T005"
    }
<br>

### T003 토큰 만료
<br>

보유하고 있는 AccessToken과 RefreshToken을 요청 헤더에 포함해 전송합니다.
API 주소는 현재 사용자가 접속을 시도하고 있는 주소로 요청합니다.

- request header
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

### 사용자 로그아웃
<br>

    POST /auth/logout

- 현재 계정에서 로그아웃합니다.
- response Body (String)
    ```json
    // 성공시
    {
        "로그아웃 성공"
    }
    // 실패시
    {
      "code": "400 Bad Request",
      "data": null
    }
<br>

### 사용자 탈퇴
<br>

    POST /auth/delete

- 현재 계정에서 탈퇴합니다.
- response Body (String)
    ```json
    // 성공시
    {
        "탈퇴 성공"
    }
    // 실패시
    {
      "code": "400 Bad Request",
      "data": null
    }
<br>

#

## 4. User Info API
<br>

  - 사용자 정보를 요청합니다.

<br>


### 사용자 기본 정보
<br>

    GET /user/info

- 사용자 기본 정보 요청 API 입니다.

- response body(application/json)
  - code : String
  - data : Object
  - `response body 예시`
    ```json
    // 성공시
    {
        "userId": "ssafy@naver.com",
        "refreshToken": null,
        "userAuthority": null,
        "nickname": "김싸피",
        "profile": "http://k.kakaocdn.net/~/img_110x110.jpg",
        "level": 0,
        "exp": 0
    }
    // 실패시
    {
      "code": "400 Bad Request",
      "data": null
    }

<br>

#

## 5. New API
<br>

  - 여기에 추가해주세요.

<br>
<br>
<br>


### 양식 이름
<br>

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