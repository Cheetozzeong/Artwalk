<template>
  <div class="d-flex container-fluid vh-100 justify-content-center">
    <div>
      <div class="d-flex justify-content-center align-content-end mt-5 mb-1">
        <img src="/img/logo.png" alt="로고 이미지" style="width: 50px; height: 50px;">
      </div>
      <b-form bg-variant="light" @submit.prevent="login" class="mt-5">
        <b-form-group
            id="group-userId"
            label="ID :"
            label-for="userId"
            label-align="left"
        >
          <b-form-input
              id="userId"
              v-model="form.userId"
              placeholder="Enter id"
              required
              class="mt-1"
          ></b-form-input>
        </b-form-group>
        <br>
        <b-form-group
            id="group-password"
            label="PASSWORD :"
            label-for="password"
        >
          <b-form-input
              id="password"
              v-model="form.password"
              placeholder="Enter password"
              type="password"
              required
              class="mt-1"
          ></b-form-input>
        </b-form-group>
        <br>
        <div class="d-flex justify-content-center">
          <b-button type="submit" style="background-color: #07A794; border: #07A794" class="px-3">LOGIN</b-button>
        </div>
      </b-form>
    </div>
  </div>
</template>

<script>

export default {
  name: "LoginView.vue",
  data() {
    return {
      form: {
        userId: '',
        password: '',
      }
    }
  },
  methods: {
    login() {
      const userId = this.form.userId
      const password = this.form.password
      const payload = {
        userId,
        password
      }
      this.$store.dispatch('login', payload)
    },
  },
}
</script>

<style scoped>

</style>