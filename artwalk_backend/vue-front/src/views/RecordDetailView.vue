<template>
  <div>레코드 디테일 {{ record }}</div>
</template>

<script>

import axios from "axios";

const API_URL = 'http://localhost:8080'

export default {
  name: "RecordDetailView.vue",
  data() {
    return {
      record: null
    }
  },
  created() {
    this.getRecordDetail()
  },
  methods: {
    getRecordDetail() {
      axios({
        method: 'get',
        url: `${API_URL}/record/${this.$route.params.recordId}`,
        headers: {'Access-Control-Allow-Origin': '*', 'accessToken': `Bearer ${this.$store.state.token}`},
      })
          .then((res) => {
            console.log(res)
            this.record = res.data
          })
          .catch((err) => {
            console.log(err)
          })
    }
  }
}
</script>

<style scoped>

</style>