<template>
  <div>
    <b-skeleton-table
        :rows="10"
        :columns="6"
        :table-props="{ bordered: true}"
    ></b-skeleton-table>
    {{ record }}
  </div>
</template>

<script>
export default {
  name: "RecordItem.vue",
  props: {
    record: Object,
  }
}
</script>

<style scoped>

</style>