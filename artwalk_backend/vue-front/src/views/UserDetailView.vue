<template>
  <div>
    <p>
      유저 디테일
    </p>
    <p>
      {{ userInfo }}
    </p>
  </div>
</template>

<script>
import axios from "axios";
const API_URL = 'http://localhost:8080'
export default {
  name: "UserDetailView.vue",
  data() {
    return {
      userInfo: null
    }
  },
  methods: {
    getUserDetail() {
      axios({
        method: 'get',
        url: `${API_URL}/user`,
        headers: {'Access-Control-Allow-Origin': '*', 'accessToken': `Bearer ${this.$store.state.token}`},
        params: {
          userId: this.$route.params.userId
        }
      })
          .then((res) => {
            console.log(res)
            this.userInfo = res.data.data
          })
          .catch((err) => {
            console.log(err)
          })
    }
  },
  created() {
    this.getUserDetail()
  }
}
</script>

<style scoped>
</style>