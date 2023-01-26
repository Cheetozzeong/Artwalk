<template>
  <b-container>
    <div class="d-flex justify-content-center align-content-center mt-5 mb-1">
      <h1>Record Board</h1>
    </div>
    <br>
    <div>
      <b-form-input placeholder="User ID">
      </b-form-input>
      <br>

      <b-table :fields="fields" :items="allRecords">
        <template #cell(title)="data">
          <router-link :to="{ name: 'recordDetail', params: { routeId: data.item.routeId } }" class="tdn maincolor">
            {{ data.item.title }}
          </router-link>
        </template>
        <template #cell(creation)="data">
          {{ data.value }}
        </template>
      </b-table>
    </div>
  </b-container>
</template>

<script>

export default {
  name: "RecordBoardView.vue",
  data() {
    return {
      fields: [
        {
          key: 'routeId',
          label: 'Route Id'
        },
        {
          key: 'userId',
          label: 'User Id'
        },
        {
          key: 'maker',
          label: 'Maker',
        },
        {
          key: 'creation',
          label: 'Creation',
          formatter: value => {
            return new Date(value).toLocaleString()
          }
        },
        {
          key: 'thumbnail',
          label: 'Thumbnail',
        },
        {
          key: 'title',
          label: 'Title',
        },
      ]
    }
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