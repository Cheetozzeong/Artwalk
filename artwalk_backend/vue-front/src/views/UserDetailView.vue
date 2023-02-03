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
import Send from "@/utils/Send";

export default {
  name: "UserDetailView.vue",
  data() {
    return {
      userInfo: null
    }
  },
  methods: {
    getUserDetail() {
      return Send({
        method: 'get',
        url: '/user',
        params: {
          userId: this.$route.params.userId
        }
      })
          .then((res) => {
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