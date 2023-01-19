import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from "@/views/LoginView.vue";
import RecordBoardView from "@/views/RecordBoardView.vue";
import UserBoardView from "@/views/UserBoardView.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/admin/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/admin/board/record',
    name: 'recordBoard',
    component: RecordBoardView
  },
  {
    path: '/admin/board/user',
    name: 'userBoard',
    component: UserBoardView
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
