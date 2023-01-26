import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

// const API_URL = 'http://localhost:8080'

export default new Vuex.Store({
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
    // GET_ROUTE(state, route) {
    //   state.route = route
    // },
    // GET_RECORD(state, record) {
    //   state.record = record
    // },
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
  },
  modules: {
  }
})
