<template>
  <div class="d-flex container-fluid vh-100 justify-content-center">
    <h1 class="my-auto">차트</h1>
  </div>
</template>

<script>
export default {
  name: "MainView.vue",
  created() {
    this.$store.dispatch('getUser')
    this.$store.dispatch('getRoute')
    this.$store.dispatch('getRecord')
  },
}

</script>

<style scoped>

</style>