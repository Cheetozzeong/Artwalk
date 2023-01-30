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

Vue.use(VueRouter)

const routes = [
  {
    path: '/admin/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/admin/main',
    name: 'main',
    component: MainView
  },
  {
    path: '/admin/board/record',
    name: 'recordBoard',
    component: RecordBoardView
  },
  {
    path: '/admin/board/route',
    name: 'routeBoard',
    component: RouteBoardView
  },
  {
    path: '/admin/board/user',
    name: 'userBoard',
    component: UserBoardView
  },
  {
    path: '/admin/board/route/:routeId',
    name: 'routeDetail',
    component: RouteDetailView
  },
  {
    path: '/admin/board/record/:recordId',
    name: 'recordDetail',
    component: RecordDetailView
  },
  {
    path: '/admin/board/user?id=:userId',
    name: 'userDetail',
    component: UserDetailView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
