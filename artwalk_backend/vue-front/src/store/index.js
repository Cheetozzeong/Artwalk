import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
import createPersistedState from 'vuex-persistedstate'
import router from "@/router";

Vue.use(Vuex)

const API_URL = 'http://localhost:8080'

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
        url: `${API_URL}/admin/login`,
        headers: {'Content-Type': 'multipart/form-data', 'Access-Control-Allow-Origin': '*'},
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
      axios({
        method: 'get',
        url: `${API_URL}/route/list`,
        headers: {'Access-Control-Allow-Origin': '*', 'accessToken': `Bearer ${context.state.token}`},
      })
          .then((res) => {
            context.commit('GET_ROUTE', res.data.routes)
          })
          .catch((err) => {
            console.log(err)
          })
    },
    getUser(context) {
      axios({
        method: 'get',
        url: `${API_URL}/user/list`,
        headers: {'Access-Control-Allow-Origin': '*', 'accessToken': `Bearer ${context.state.token}`},
      })
          .then((res) => {
            context.commit('GET_USER', res.data.data)
          })
          .catch((err) => {
            console.log(err)
          })
    },
    getRecord(context) {
      axios({
        method: 'get',
        url: `${API_URL}/route/list/`,
        headers: {'Access-Control-Allow-Origin': '*', 'accessToken': `Bearer ${context.state.token}`},
      })
          .then((res) => {
            context.commit('GET_RECORD', res.data.routes)
          })
          .catch((err) => {
            console.log(err)
          })
    },
  },
  modules: {
  }
})
