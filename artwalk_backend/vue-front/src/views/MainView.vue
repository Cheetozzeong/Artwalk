<template>
  <div class="d-flex container-fluid vh-100 p-4 row">

    <div class="row my-3">
      <UserChart
          class="col-6"
          :normalUser="normalUser"
          :socialUser="socialUser"
      />
      <ByDistanceChart
          class="col-6"
          :routeDistance="routeDistance"
          :recordDistance="recordDistance"
      />
    </div>

    <div class="row my-3">
      <ByDayChart
          class="col-6"
          :byDayRoute="byDayRoute"
          :byDayRecord="byDayRecord"
      />
      <MonthlyChart
          class="col-6"
          :byMonthRoute="byMonthRoute"
          :byMonthRecord="byMonthRecord"
      />
    </div>

  </div>
</template>

<script>

import ByDayChart from "@/components/ByDayChart.vue";
import MonthlyChart from "@/components/MonthlyChart.vue";
import UserChart from "@/components/UserChart.vue";
import ByDistanceChart from "@/components/ByDistanceChart.vue";
import dayjs from "dayjs";
import 'dayjs/locale/ko';
dayjs.locale('ko');

export default {
  name: "MainView.vue",
  components:{
    UserChart,
    MonthlyChart,
    ByDayChart,
    ByDistanceChart,
  },
  data() {
    return {
      socialUser: 0,
      normalUser: 0,
      routeDistance: {'0 - 3km': 0, '3 - 10km':0, '10 - 30km':0, '30 - 50km':0, '50 - 100km':0, '100 - 300km':0, '300km 이상':0},
      recordDistance: {'0 - 3km': 0, '3 - 10km':0, '10 - 30km':0, '30 - 50km':0, '50 - 100km':0, '100 - 300km':0, '300km 이상':0},
      byDayRoute: {'월': 0, '화': 0, '수': 0, '목': 0, '금': 0, '토': 0, '일':0},
      byDayRecord: {'월': 0, '화': 0, '수': 0, '목': 0, '금': 0, '토': 0, '일':0},
      byMonthRoute: {1: 0, 2: 0, 3: 0, 4: 0, 5: 0, 6: 0, 7: 0, 8: 0, 9: 0, 10: 0, 11: 0, 12: 0},
      byMonthRecord: {1: 0, 2: 0, 3: 0, 4: 0, 5: 0, 6: 0, 7: 0, 8: 0, 9: 0, 10: 0, 11: 0, 12: 0},
    }
  },
  methods: {
    checkUserCount() {
      for (const user of this.allUsers) {
        if (user.userType === 'normal') {
          this.normalUser += 1
        } else {
          this.socialUser += 1
        }
      }
    },
    checkAll() {
      for (const Route of this.allRoutes) {
        if (Route.distance <= 3000) {
          this.routeDistance['0 - 3km'] += 1;
        } else if (Route.distance <= 10000) {
          this.routeDistance['3 - 10km'] += 1;
        } else if (Route.distance <= 30000) {
          this.routeDistance['10 - 30km'] += 1;
        } else if (Route.distance <= 50000) {
          this.routeDistance['30 - 50km'] += 1;
        } else if (Route.distance <= 100000) {
          this.routeDistance['50 - 100km'] += 1;
        } else if (Route.distance <= 300000) {
          this.routeDistance['100 - 300km'] += 1;
        } else {
          this.routeDistance['300km 이상'] += 1;
        }
        this.byDayRoute[dayjs(Route.creation).locale('ko').format('ddd')] += 1
        this.byMonthRoute[parseInt(dayjs(Route.creation).format('MM'), 10)] += 1
      }
      for (const Record of this.allRecords) {
        if (Record.distance <= 3000) {
          this.recordDistance['0 - 3km'] += 1;
        } else if (Record.distance <= 10000) {
          this.recordDistance['3 - 10km'] += 1;
        } else if (Record.distance <= 30000) {
          this.recordDistance['10 - 30km'] += 1;
        } else if (Record.distance <= 50000) {
          this.recordDistance['30 - 50km'] += 1;
        } else if (Record.distance <= 100000) {
          this.recordDistance['50 - 100km'] += 1;
        } else if (Record.distance <= 300000) {
          this.recordDistance['100 - 300km'] += 1;
        } else {
          this.recordDistance['300km 이상'] += 1;
        }
        this.byDayRecord[dayjs(Record.creation).locale('ko').format('ddd')] += 1
        this.byMonthRecord[parseInt(dayjs(Record.creation).format('MM'), 10)] += 1
      }
    },
  },
  computed: {
    allUsers() {
      return this.$store.state.user
    },
    allRoutes() {
      return this.$store.state.route
    },
    allRecords() {
      return this.$store.state.record
    },
  },
  created() {
    this.$store.dispatch('getUser')
    this.$store.dispatch('getRoute')
    this.$store.dispatch('getRecord')
    this.checkUserCount()
    this.checkAll()
  },
}

</script>

<style scoped>

</style>