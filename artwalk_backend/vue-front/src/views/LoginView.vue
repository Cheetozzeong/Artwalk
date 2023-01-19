<template>
  <div>
    <b-form bg-variant="light" @submit.prevent="login">
      <b-form-group
          id="group-userid"
          label="Your id:"
          label-for="userid"
          label-align="left"
      >
        <b-form-input
            id="userid"
            v-model="form.userid"
            placeholder="Enter id"
            required
        ></b-form-input>
      </b-form-group>
      <b-form-group
          id="group-password"
          label="Your password:"
          label-for="password"
      >
        <b-form-input
            id="password"
            v-model="form.password"
            placeholder="Enter password"
            type="password"
            required
        ></b-form-input>
      </b-form-group>
      <b-button type="submit" variant="primary">Submit</b-button>
    </b-form>
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