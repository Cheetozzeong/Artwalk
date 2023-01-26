<template>
  <div class="d-flex container-fluid vh-100 justify-content-center">
    <div>
      <div class="d-flex justify-content-center align-content-center mt-5 mb-1">
        <h1>Art Walk</h1>
      </div>
      <br>
      <b-form bg-variant="light" @submit.prevent="login" class="mt-5">
        <b-form-group
            id="group-userid"
            label="ID :"
            label-for="userid"
            label-align="left"
        >
          <b-form-input
              id="userid"
              v-model="form.userid"
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
        userid: '',
        password: '',
      }
    }
  },
  // methods: {
  //   login() {
  //     const userid = this.userid
  //     const password = this.password
  //     const payload = {
  //       userid,
  //       password,
  //     }
  //     this.$store.dispatch('login', payload)
  //   },
  // }
}
</script>

<style scoped>

</style>