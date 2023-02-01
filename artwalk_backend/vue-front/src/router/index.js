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

Vue.use(VueRouter)

const routes = [
  {
    path: '/admin/',
    name: 'login',
    component: LoginView
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
    path: '/admin/board/user/:userId/',
    name: 'userDetail',
    component: UserDetailView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  const allowPages = ['login']
  const isAuthRequired = !allowPages.includes(to.name)

  if (!store.state.isLogin && isAuthRequired) {
    alert('로그인이 필요한 서비스 입니다.')
    next({ name: 'login' })
  } else {
    next()
  }
})

export default router
