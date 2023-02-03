<template>
  <div>루트 디테일 {{ route }}
    <img id="thumbnail" :src="thumbUrl" alt="Thumbnail Image">
  </div>
</template>

<script>
import Send from "@/utils/Send";

export default {
  name: "RouteDetailView.vue",
  data() {
    return {
      route: null,
      thumbUrl: null
    }
  },
  created() {
    this.getRouteDetail()
    this.getRouteImage()
  },
  methods: {
    getRouteDetail() {
      return Send({
        method: 'get',
        url: `/route/${this.$route.params.routeId}`,
      })
          .then((res) => {
            this.route = res.data
          })
          .catch((err) => {
            console.log(err)
          })
    },
    getRouteImage() {

      const url = `/route/thumb/${this.$route.params.routeId}`
      const options = {
        headers: {
          'Access-Control-Allow-Origin': '*',
          'accessToken': `Bearer ${this.$store.state.token}`
        }
      }

      fetch(url, options)
          .then(res => res.blob())
          .then(blob => {
            console.log(blob)
            this.thumbUrl = URL.createObjectURL(blob)
          })
    },
  }
}
</script>

<style scoped>

</style>
