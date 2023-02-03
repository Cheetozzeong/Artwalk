import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
import createPersistedState from 'vuex-persistedstate'
import router from "@/router";
import Send from "@/utils/Send";

Vue.use(Vuex)


export default new Vuex.Store({
  plugins: [
    createPersistedState({
      storage: window.sessionStorage
    })
  ],

  state: {
    token: null,
    route: [],
    record: [],
    user: [],
    isLogin: false
  },
  getters: {
  },
  mutations: {
    SAVE_TOKEN(state, token) {
      state.token = token
      router.push({ name: 'main' })
    },
    LOG_IN(state) {
      state.isLogin = true
    },
    LOG_OUT(state) {
      state.isLogin = false
      state.token = null
    },
    GET_ROUTE(state, route) {
      state.route = route
    },
    GET_RECORD(state, record) {
      state.record = record
    },
    GET_USER(state, user) {
      state.user = user
    }
  },
  actions: {
    login(context, payload) {
      axios({
        method: 'post',
        // TODO: 주소 변경 필요
        url: '/admin/login',
        headers: {'Content-Type': 'multipart/form-data'},
        data: {
          userId: `${payload.userId}`,
          password: `${payload.password}`
        }
      })
          .then((res) => {
            context.commit('LOG_IN')
            context.commit('SAVE_TOKEN', res.headers.accesstoken)
          })
          .catch((err) => {
            console.log(err)
            alert('아이디 혹은 비밀번호를 확인해주세요.')
          })
    },
    logout(context) {
      context.commit('LOG_OUT')
    },
    getRoute(context) {
      return Send({
        method: 'get',
        url: '/route/list',
        params: {
          user: false
        }
      })
          .then((res) => {
            context.commit('GET_ROUTE', res.data.data)
          })
          .catch((err) => {
            console.log(err)
          })
    },
    getRecord(context) {
      return Send({
        method: 'get',
        url: '/record/list',
        params: {
          user: false
        }
      })
          .then((res) => {
            context.commit('GET_RECORD', res.data.data)
          })
          .catch((err) => {
            console.log(err)
          })
    },
    getUser(context) {
      return Send({
        method: 'get',
        url: '/user/list',
      })
          .then((res) => {
            context.commit('GET_USER', res.data.data)
          })
          .catch((err) => {
            console.log(err)
          })
    }
  },
  modules: {
  }
})