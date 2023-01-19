import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: null
  },
  getters: {
  },
  mutations: {
    // SAVE_TOKEN(state, token) {
    //   state.token = token
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
