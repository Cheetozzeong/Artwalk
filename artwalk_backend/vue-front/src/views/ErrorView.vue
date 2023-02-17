<template>
  <div>
    <img src="/img/logo_wrong.png" alt="잘못된 접근 이미지">
    <h2>잘못된 접근입니다.</h2>
    <p>인증에 실패하여 페이지에 접근할 수 없습니다
      <br>해당 기능을 제공한 서비스 관리자에게 문의해주세요.
    </p>
    <button @click="goBack">뒤로 가기</button>
  </div>
</template>

<script>

export default {
  name: "ErrorView.vue",
  created() {
      this.$store.state.isError = true
    },
  methods: {
    goBack() {
      if (this.$store.state.isLogin) {
        this.$router.back()
        this.$store.state.isError = false
      } else {
        this.$router.push({name: 'login'})
        this.$store.state.isError = false
      }
    }
  }
}
</script>

<style scoped>
div {
  width: 100%;
  height: 100vh;
  text-align: center;
  padding-block: 20vh;
}

img{
  width: 150px;
  margin: 20px;
}

p {
  color: #5e5e5e
}

button {
  padding: 15px 25px 18px;
  gap: 2px;
  background: #07A794;
  box-shadow: 0px 8px 14px rgba(0, 0, 0, 0.15);
  border-radius: 30px;
  font-weight: bolder;
  font-size: 14px;
  line-height: 21px;
  color: #ffffff;
  border: #07A794;
  cursor: pointer;
  margin: 20px;
}
button:hover {
  background: #3CBBB0;
}
</style>