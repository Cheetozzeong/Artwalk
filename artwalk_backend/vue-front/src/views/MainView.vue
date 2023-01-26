<template>
  <div>
    <h1>차트</h1>
  </div>
</template>

<script>
export default {
  name: "MainView.vuew",
  created() {
    this.getRoute()
    // this.getUser()
    // this.getRecord()
  },
  methods: {
    getRoute() {
      this.$store.dispatch('getRoute')
    },
    // getUser() {
    //   this.$store.dispatch('getUser')
    // },
    // getRecord() {
    //   this.$store.dispatch('getRecord')
    // },
  },
}

</script>

<style scoped>

</style>