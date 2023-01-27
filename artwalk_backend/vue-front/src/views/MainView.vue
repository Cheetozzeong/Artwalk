<template>
  <div class="d-flex container-fluid vh-100 justify-content-center">
    <h1 class="my-auto">차트</h1>
  </div>
</template>

<script>
export default {
  name: "MainView.vue",
  created() {
    this.getUser()
    this.getRecord()
    this.getRoute()
  },
  methods: {
    getUser() {
      this.$store.dispatch('getUser')
    },
    getRecord() {
      this.$store.dispatch('getRecord')
    },
    getRoute() {
      this.$store.dispatch('getRoute')
    }
  },
}

</script>

<style scoped>

</style>