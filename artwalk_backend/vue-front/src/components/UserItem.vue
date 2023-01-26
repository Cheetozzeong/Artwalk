<template>
  <div>
    <b-skeleton-table
        :rows="5"
        :columns="4"
        :table-props="{ bordered: true}"
    ></b-skeleton-table>
    {{ user }}
  </div>
</template>

<script>
export default {
  name: "UserItem.vue",
  props: {
    user: Object
  }
}
</script>

<style scoped>

</style>