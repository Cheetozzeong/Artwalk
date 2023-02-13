<template>
  <b-col cols="3">
    <b-card
        img-top
        :img-src="thumbUrl"
        tag="article"
        style="max-width: 20rem;"
        class="mb-2 m-click"
        @click="goDetail"
    >
      <b-card-title v-if="record.detail">
        {{ record.detail }}
      </b-card-title>

      <b-card-title v-else>
        No Title
      </b-card-title>

      <b-card-text>
        <p>Record Id : {{ record.recordId }}</p>
        <p>{{ record.userId }}</p>
        <p>{{ formattedCreation }}</p>
      </b-card-text>

      <b-card-sub-title>
        <p>Duration - {{ formattedDuration }}</p>
        <p>Distance - {{ formattedDistance }}</p>
      </b-card-sub-title>

    </b-card>
  </b-col>
</template>

<script>

import dayjs from "dayjs";

export default {
  name: "RecordItem.vue",
  props: {
    record: Object,
  },
  data() {
    return {
      date: this.record.creation,
      formattedCreation: dayjs(this.date).format('YYYY/MM/DD'),
      formattedDuration: null,
      formattedDistance: Math.round(this.record.distance).toLocaleString('ko-KR') + " M",
      thumbUrl: null,
    }
  },
  created() {
    this.getTimeStringSeconds(this.record.duration)
    this.getRecordImage()
  },
  methods: {
    goDetail() {
      this.$router.push({ name: 'recordDetail', params: { recordId: this.record.recordId } })
    },
    getTimeStringSeconds(seconds) {
      let hour, min, sec
      hour = parseInt((seconds*1) / 3600)
      min = parseInt((seconds*1 % 3600) / 60)
      sec = Math.floor(seconds*1 % 60)
      if (hour.toString().length == 1) hour = "0" + hour
      if (min.toString().length == 1) min = "0" + min
      if (sec.toString().length == 1) sec = "0" + sec

      this.formattedDuration = hour + ":" + min + ":" + sec
    },
    getRecordImage() {
      const url = `/record/thumb/${this.record.recordId}`
      const options = {
        headers: {
          'Access-Control-Allow-Origin': '*',
          'accessToken': `Bearer ${this.$store.state.token}`
        }
      }

      fetch(url, options)
          .then(res => res.blob())
          .then(blob => {
            this.thumbUrl = URL.createObjectURL(blob)
          })
    },
  },
}
</script>

<style scoped>

</style>