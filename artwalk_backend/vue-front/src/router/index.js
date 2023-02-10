import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from "@/views/LoginView.vue";
import RecordBoardView from "@/views/RecordBoardView.vue";
import UserBoardView from "@/views/UserBoardView.vue";
import MainView from "@/views/MainView.vue";
import RouteBoardView from "@/views/RouteBoardView.vue";
import RouteDetailView from "@/views/RouteDetailView.vue";
import RecordDetailView from "@/views/RecordDetailView.vue";
import UserDetailView from "@/views/UserDetailView.vue";
import store from "@/store";
import ErrorView from "@/views/ErrorView.vue";

Vue.use(VueRouter)


const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/admin/',
      name: 'login',
      component: LoginView,
      beforeEnter: (to, from, next) => {
        if (store.state.token !== null) {
          alert("현재 로그인 상태입니다. \n로그인 페이지로는 돌아갈 수 없습니다.")
          next({ name: 'main' });
        } else {
          next()
        }
      },
    },
    {
      path: '/admin/main/',
      name: 'main',
      component: MainView
    },
    {
      path: '/admin/board/record/',
      name: 'recordBoard',
      component: RecordBoardView
    },
    {
      path: '/admin/board/route/',
      name: 'routeBoard',
      component: RouteBoardView
    },
    {
      path: '/admin/board/user/',
      name: 'userBoard',
      component: UserBoardView
    },
    {
      path: '/admin/board/route/:routeId/',
      name: 'routeDetail',
      component: RouteDetailView
    },
    {
      path: '/admin/board/record/:recordId/',
      name: 'recordDetail',
      component: RecordDetailView
    },
    {
      path: '/admin/board/user?userId=:userId/',
      name: 'userDetail',
      component: UserDetailView
    },
    {
      path: '/admin/*',
      name:'error',
      component: ErrorView,
    },
  ]
})

router.beforeEach((to, from, next) => {
  const allowPages = ['login', 'error']
  const isAuthRequired = !allowPages.includes(to.name)

  if (!store.state.isLogin && isAuthRequired) {
    alert('로그인이 필요한 서비스 입니다.')
    next({ name: 'login' })
  } else {
    next()
  }
})

export default router
