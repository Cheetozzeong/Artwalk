<template>
  <b-col cols="3">
    <b-card
        img-top
        tag="article"
        style="max-width: 20rem;"
        class="mb-2 m-click"
        @click="goDetail"
    >
      <b-card-title>
        {{ route.title }}
      </b-card-title>

      <b-card-img>
      </b-card-img>

      <b-card-text>
        <p>Route Id : {{ route.routeId }}</p>
        <p>{{ route.userId }}</p>
        <p>{{ formattedCreation }}</p>
      </b-card-text>

      <b-card-sub-title>
        <p>Made by - {{ route.maker }}</p>
      </b-card-sub-title>

    </b-card>
  </b-col>
</template>

<script>

import dayjs from "dayjs";
export default {
  name: "RouteItem.vue",
  props: {
    route: Object,
  },
  data() {
    return {
      date: this.route.creation,
      formattedCreation: dayjs(this.date).format('YYYY/MM/DD')
    }
  },
  methods: {
    goDetail() {
      this.$router.push({ name: 'routeDetail', params: { routeId: this.route.routeId } })
    }
  }
}
</script>

<style scoped>

</style>