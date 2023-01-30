<template>
  <b-container>
    <div class="d-flex justify-content-center align-content-center mt-5 mb-1">
      <h1>Route Board</h1>
    </div>
    <br>
    <div>
      <!--   드롭 다운 버튼   -->
      <div class="d-flex justify-content-end">
        <b-dropdown id="dropdown-1" :text="selectedDropdownItem" class="m-1" style="height: 38px;">
          <b-dropdown-item v-for="option in options" :key="option" :value="option" @click="changeCategory(option)">
            {{ option }}
          </b-dropdown-item>
        </b-dropdown>
        <b-form @submit.prevent="goSearch" @reset.prevent="doReset" class="d-block">
          <b-form-input :placeholder="selectedDropdownItem" v-model="searchKeyword" class="w-100 d-inline-block m-1">
          </b-form-input>
          <div class="d-flex justify-content-end">
            <b-button type="submit" style="background-color: #3c3c3c; border-color: #3c3c3c;" class="m-1">Submit</b-button>
            <b-button type="reset" style="background-color: rgba(211,47,47,0.8); border-color: rgba(211,47,47,0.8);" class="m-1">Reset</b-button>
          </div>
        </b-form>
      </div>
      <br>

      <b-table :fields="fields" :items="allRoutes" sticky-header responsive>
        <template #cell(title)="data">
          <router-link :to="{ name: 'routeDetail', params: { routeId: data.item.routeId } }"
                       class="tdn maincolor">
            {{ data.item.title }}
          </router-link>
        </template>
        <template #cell(creation)="data">
          {{ data.value }}
        </template>
      </b-table>

      <RouteItem
          v-for="allRoute in allRoutes"
          :key="allRoute.routeId"
          :route="allRoute"
      />
    </div>
  </b-container>
</template>

<script>

import RouteItem from "@/components/RouteItem.vue";

export default {
  name: "RouteBoardView.vue",
  components: {
    RouteItem,
  },
  data() {
    return {
      selectedDropdownItem: "not Selected",
      searchKeyword: null,
      options: ['userId', 'maker', 'routeId'],
      fields: [
        {
          key: 'routeId',
          label: 'Route Id'
        },
        {
          key: 'title',
          label: 'Title',
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
      ]
    }
  },
  methods: {
    selectKeyword(e) {
      this.selectedDropdownItem = e
      return
    },
    goSearch() {
      console.log(this.searchKeyword)
      return
    }
  },
  computed: {
    allRoutes() {
      return this.$store.state.route
    },
  }
}
</script>

<style scoped>

</style>