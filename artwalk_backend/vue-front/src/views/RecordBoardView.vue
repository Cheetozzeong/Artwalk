<template>
  <div>
    <p>레코드보드</p>
    <RecordItem/>
  </div>
</template>

<script>
import RecordItem from "@/components/RecordItem.vue";

export default {
  name: "RecordBoardView.vue",
  components: {
    RecordItem
  }
}
</script>

<style scoped>

</style>