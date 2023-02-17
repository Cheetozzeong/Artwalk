<div align="center">
    <img src="./readme_assets/artwalk_logo.png" width="50px" alt="artwalk_logo"/> <h1><b>귿다</b></h1>
</div>
<br>

## 목차

1. [**프로젝트 소개**](#1-프로젝트-소개)
2. [**기술 스택**](#2-기술-스택)
3. [**서비스 화면**](#3-서비스-화면)
4. [**서비스 구성 요소**](#4-서비스-구성-요소)
5. [**기능 소개**](#5-구현-기능)
6. [**개발팀 소개**](#6-개발팀-소개)

<br>

# **프로젝트 소개**

<p align="justify">

<img src="./readme_assets/artwalk_main.png" alt="artwalk_main" />

<br>

**귿다**는 GPS 트래킹 기록을 이용해 멋진 지도 위 그림을 그릴 수 있는 안드로이드 어플리케이션입니다.

어플리케이션에서 지도 위 원하는 좌표를 선택해 이동하고자 하는 경로의 밑그림을 그릴 수 있습니다.

밑그림을 바탕으로 경로를 따라 이동하고, 저장 및 다른 사용자와 멋진 GPS 아트를 공유할 수 있습니다.

<br>

👉 ['귿다' 다운로드를 위해 플레이스토어로 이동하기](#https://play.google.com/store/apps/details?id=com.a401.artwalk&pli=1) 👈

</p>

<br>

# ⚙ **기술 스택**

<br>

### **Android**

| JAVA | Kotlin | Jetpack SDK |
| :---: | :---: | :---: |
| <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbWwcEv%2FbtrZcgCxHKb%2FyDsPPR4smbDskPg52phN80%2Fimg.png" alt="Java" width="50px" height="50px" /> | <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FRRygX%2FbtrZzauY4ir%2F316G5KGVxCWE9HECunTBpk%2Fimg.png" alt="Kotlin" width="40px" height="40px" /> | <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FAY3At%2FbtrYY8fAzBZ%2F3tUkFWwRO3OtbejIJJxrsK%2Fimg.png" alt="Jetpack" width="50px" height="50px" /> |


- Mapbox API
- Android Jetpack
    - Databinding, Navigation, Hilt
- Coroutine / Flow
- MVVM / Clean Architecture
- Retrofit2

<br>

### **Front-end**

| HTML5 | CSS3 | JavaScript | Vue.js | Node.js |
| :---: | :---: | :---: | :---: | :---: |
| <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FocjqF%2FbtrYY6a0CVT%2F7Vj9L8LsyVg0VYIKHKhRrK%2Fimg.png" alt="HTML5" width="50px" height="50px" /> | <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FBxG0A%2FbtrZsGoaogk%2FP2tz1ilK495pKJnYRMkp10%2Fimg.png" alt="CSS3" width="50px" height="50px" /> | <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F0oCe0%2FbtrZrZ9jYNc%2FslPBAUV0VsHCWH5v48Xiik%2Fimg.png" alt="JavaScript" width="50px" height="50px" /> | <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdNkY16%2FbtrY4mRRfww%2F98mmcvW6mITKylPZKdnur0%2Fimg.png" alt="Vuejs" width="50px" height="50px" /> |<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fd9JYaI%2FbtrZre0kM58%2FbafqdiLN7IkmqWdhVeYiS1%2Fimg.png" alt="Node.js" width="50px" height="50px" /> |

<br>

### **Back-end**

| JAVA | Spring-Boot | MySQL | Swagger |
| :---: | :---: | :---: | :---: |
| <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbWwcEv%2FbtrZcgCxHKb%2FyDsPPR4smbDskPg52phN80%2Fimg.png" alt="Java" width="50px" height="50px" /> | <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbflVMi%2FbtrZtrxr3JP%2F6LR8wKcW07oPvFi0em7BT1%2Fimg.png" alt="Spring-Boot" height="50px" /> | <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fc3WUjy%2FbtrZresoXSc%2FWBKMNJJ5Ti7rlwdY5tEKUk%2Fimg.png" alt="Mysql" width="50px" height="50px" /> | <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FpkrrM%2FbtrYYqmUIQ2%2FVM6B9FMdBkBrXi5KSZk1FK%2Fimg.png" alt="Swagger" width="50px" height="50px" /> |


<br>

### **DevOps**

| AWS EC2 | NGINX | Jenkins | Docker | Gitlab |
| :---: | :---: | :---: | :---: | :---: |
| <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fba5UVu%2FbtrZqThHJU2%2FWD4v5UB5EUzOZ1USmKsnj1%2Fimg.png" alt="ec2" width="50px" height="50px" /> | <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbx9Wuu%2FbtrZttozuzv%2FB0NX8TM0N3GbRl4v7fz9rk%2Fimg.png" alt="nginx" width="50px" height="50px" /> | <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FmbePB%2FbtrZtqk3oUR%2FR4JdtGvANlkjkjOCZxtNH0%2Fimg.png" alt="Jenkins" width="50px" height="50px" /> | <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcfetHF%2FbtrZqzqgWhb%2FC5UmfoqZSBURzHV4lF7Nk1%2Fimg.png" alt="docker" width="50px" height="50px" /> | <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FwjjyX%2FbtrY1lZXb3G%2FWLqUx75LkkiIQCKpodBIwk%2Fimg.png" alt="gitlab" width="50px" height="50px" /> |

<br>

# 🔍 **서비스 화면**

<br>

<img src="./readme_assets/artwalk_service_screen_1.png" alt="artwalk_service_screen" />

<img src="./readme_assets/artwalk_service_screen_2.png" alt="artwalk_service_screen" />

<img src="./readme_assets/artwalk_service_screen_3.png" alt="artwalk_service_screen" />

<img src="./readme_assets/artwalk_service_screen_4.png" alt="artwalk_service_screen" />

<img src="./readme_assets/artwalk_service_screen_5.png" alt="artwalk_service_screen" />

<br>

👉 [애플리케이션 시연 영상 보러가기](#https://youtu.be/0vgKAfpSR84) 👈

<br>

# 관리자 페이지

<br>

## 로그인
<img src="./readme_assets/admin_page_screen_1.png" alt="admin_page_screen" />

<br>

## 메인
<img src="./readme_assets/admin_page_screen_2.png" alt="admin_page_screen" />

<br>

- 전체 유저, 기록, 경로 데이터를 이용하여 제작된 차트를 확인할 수 있다.

<br>

## 사이드바
<img src="./readme_assets/admin_page_screen_3.png" alt="admin_page_screen" />

<br>

- 좌상단에 위치한 버튼을 클릭하여 사이드바를 열고 사이드바를 통해 메인, 회원게시판, 경로게시판, 기록게시판으로 이동할 수 있다.

<br>

## 유저
<img src="./readme_assets/admin_page_screen_4.png" alt="admin_page_screen" />
<img src="./readme_assets/admin_page_screen_5.png" alt="admin_page_screen" />

<br>

- 모든 회원의 아이디, 닉네임, 분류, 경로수, 기록수, 가입일, 최근 접속일을 확인 할 수 있다.
- 아이디, 닉네임으로 특정 회원을 검색할 수 있다.
- 상세페이지에서 각 회원의 프로필 이미지와 해당 회원이 작성한 경로, 기록을 추가로 조회하고 삭제할 수 있다.

<br>

## 경로
<img src="./readme_assets/admin_page_screen_6.png" alt="admin_page_screen" />
<img src="./readme_assets/admin_page_screen_7.png" alt="admin_page_screen" />

<br>

- 생성된 모든 경로의 썸네일, 타이틀, 경로아이디, 생성한 회원의 아이디, 생성일을 확인 할 수 있다.
- 경로 타이틀, 생성한 회원 아이디로 특정 경로를 검색할 수 있다.
- 상세페이지에서 각 경로의 썸네일, 예상시간, 거리를 추가로 조회하고 해당 경로를 삭제할 수 있다.

<br>

## 기록
<img src="./readme_assets/admin_page_screen_8.png" alt="admin_page_screen" />
<img src="./readme_assets/admin_page_screen_9.png" alt="admin_page_screen" />

<br>

- 생성된 모든 기록의 썸네일, 타이틀, 기록아이디, 생성한 회원의 아이디, 생성일을 확인 할 수 있다.
- 기록 타이틀, 생성한 회원 아이디로 특정 기록을 검색할 수 있다.
- 상세페이지에서 각 기록의 썸네일, 소요시간, 거리를 추가로 조회하고 해당 기록을 삭제할 수 있다.

<br>

# 🧵 **서비스 구성요소**
<br>

## 아키텍처

<img src="./readme_assets/artwalk_architecture.png" alt="artwalk_architecture" />


<br>

## ERD 구조

<img src="./readme_assets/artwalk_erd_diagram.png" alt="artwalk_erd_diagram" />

<br>

# **개발 팀 소개**

<br>

## 📋**개발 일정**
2023.01.05 ~ 2023.02.17

<br>

### **Android**
| <img src="./readme_assets/치헌.png" alt="chi" width="50px" height="50px" /> | <img src="./readme_assets/은빈.png" alt="go" width="50px" height="50px" /> |
| :---: | :---: |
| 이치헌 | 고은빈 |
| 팀장 | 팀원 |

<br>

### **Web (Front-end & Back-end)**

| <img src="./readme_assets/예찬.png" alt="gong" width="50px" height="50px" /> | <img src="./readme_assets/수빈.png" alt="moon" width="50px" height="50px" /> | <img src="./readme_assets/창준.png" alt="chang" width="50px" height="50px" /> | <img src="./readme_assets/유정.png" alt="jung" width="50px" height="50px" /> |
| :---: | :---: | :---: | :---: |
| 공예찬 | 문수빈 | 이창준 | 정유정 |
| 팀원 | 팀원 | 팀원 | 팀원 |

<br>

<!-- ## 👏 프로젝트 기여

| 이름 | 역할 |
| :---: | :---: |
| 이치헌 | -  |
| 고은빈 | -  |
| 공예찬 | -  |
| 문수빈 | -  |
| 이창준 | -  |
| 정유정 | -  |

<br>

<p align="justify">

</p>

<br> -->