import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
import createPersistedState from 'vuex-persistedstate'

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
  },
  getters: {
  },
  mutations: {
    // SAVE_TOKEN(state, token) {
    //   state.token = token
    // },
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
    // login(context, payload) {
    //   axios({
    //     method: 'post',
    //     url: '',
    //     data: {
    //       username: payload.username,
    //       password: payload.password,
    //     }
    //   })
    //       .then((res) => {
    //         // console.log(res)
    //         context.commit('SAVE_TOKEN', res.data.key)
    //       })
    //       .catch((err) => {
    //         console.log(err)
    //         alert('아이디 혹은 비밀번호를 확인해주세요.')
    //       })
    // },
    getRoute(context) {
      axios({
        method: 'get',
        url: `${API_URL}/route/list`,
        headers: {'Access-Control-Allow-Origin': '*'},
      })
          .then((res) => {
            console.log(res)
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
        headers: {'Access-Control-Allow-Origin': '*'},
      })
          .then((res) => {
            console.log(res)
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
        headers: {'Access-Control-Allow-Origin': '*'},
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
