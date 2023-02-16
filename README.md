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

/ TODO: 멋진 프로젝트 이미지

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

// TODO: 시연 영상 추가하기

👉 [애플리케이션 시연 영상 보러가기](#https://play.google.com/store/apps/details?id=com.a401.artwalk&pli=1) 👈

<br>

<img src="./readme_assets/artwalk_service_screen_1.png" alt="artwalk_service_screen" />

<img src="./readme_assets/artwalk_service_screen_2.png" alt="artwalk_service_screen" />

<img src="./readme_assets/artwalk_service_screen_3.png" alt="artwalk_service_screen" />

<img src="./readme_assets/artwalk_service_screen_4.png" alt="artwalk_service_screen" />

<img src="./readme_assets/artwalk_service_screen_5.png" alt="artwalk_service_screen" />


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
| <img src="" alt="chi" width="50px" height="50px" /> | <img src="" alt="go" width="50px" height="50px" /> |
| :---: | :---: |
| 이치헌 | 고은빈 |
| 팀장 | 팀원 |

<br>

### **Web (Front-end & Back-end)**

| <img src="" alt="gong" width="50px" height="50px" /> | <img src="" alt="moon" width="50px" height="50px" /> | <img src="" alt="chang" width="50px" height="50px" /> | <img src="" alt="jung" width="50px" height="50px" /> |
| :---: | :---: | :---: | :---: |
| 공예찬 | 문수빈 | 이창준 | 정유정 |
| 팀원 | 팀원 | 팀원 | 팀원 |

<br>

## 👏 프로젝트 기여

| 이름 | 역할 |
| :---: | :---: |
| 이치헌 | - 담당한 역할 |
| 고은빈 | - 담당한 역할 |
| 공예찬 | - 담당한 역할 |
| 문수빈 | - 담당한 역할 |
| 이창준 | - 담당한 역할 |
| 정유정 | - 담당한 역할 |

<br>

<p align="justify">

</p>

<br>


# 프로젝트 실행 방법

/ TODO: 프로젝트 완성 후 실행 방법 정리