<template>
  <div>레코드 디테일 {{ record }}
    <img id="thumbnail" :src="thumbUrl" alt="Thumbnail Image">
  </div>
</template>

<script>

import Send from "@/utils/Send";

export default {
  name: "RecordDetailView.vue",
  data() {
    return {
      record: null,
      thumbUrl: null
    }
  },
  created() {
    this.getRecordDetail()
    this.getRecordImage()
  },
  methods: {
    getRecordDetail() {
      return Send({
        method: 'get',
        url: `/record/${this.$route.params.recordId}`,
      })
          .then((res) => {
            this.record = res.data
          })
          .catch((err) => {
            console.log(err)
          })
    },
    getRecordImage() {
      const url = `/record/thumb/${this.$route.params.recordId}`
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