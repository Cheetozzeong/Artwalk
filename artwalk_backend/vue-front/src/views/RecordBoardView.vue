<template>
  <b-container>
    <div class="d-flex justify-content-center align-content-center mt-5 mb-1">
      <h1>Record Board</h1>
    </div>
    <br>
    <div>

      <!--   레코드 리스트 카드 - RecordItem 으로 props   -->
      <b-row>
        <RecordItem
            v-for="allRecord in allRecords"
            :key="allRecord.recordId"
            :record="allRecord"
        />
      </b-row>

    </div>
  </b-container>
</template>

<script>

import RecordItem from "@/components/RecordItem.vue";


export default {
  name: "RecordBoardView.vue",
  components: {
    RecordItem
  },
  computed: {
    allRecords() {
      return this.$store.state.record
    },
  }
}
</script>

<style scoped>

</style>