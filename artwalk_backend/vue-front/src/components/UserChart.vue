<template>
  <div style="height: 35vh">
    <b> - User SignUp Type Chart</b><br>
      <Doughnut
          :options="options"
          :data="data"
          :chart-id="chartId"
          :dataset-id-key="datasetIdKey"
      />
  </div>
</template>

<script>
import { Doughnut } from 'vue-chartjs'
import {Chart as ChartJS, Title, Tooltip, Legend, ArcElement, CategoryScale} from 'chart.js'
ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale)

export default {
  name: 'UserChart.vue',
  components: {
    Doughnut
  },
  props: {
    chartId: {
      type: String,
      default: 'doughnut-chart'
    },
    datasetIdKey: {
      type: String,
      default: 'label'
    },
    socialUser: {
      type: Number,
    },
    normalUser: {
      type: Number,
    },
    // users: {
    //   type: Array,
    // }
  },
  // methods: {
  //   checkUserCount() {
  //     for (const user of this.users) {
  //       if (user.userType === 'normal') {
  //         this.normalUser += 1
  //       } else {
  //         this.socialUser += 1
  //       }
  //     }
  //   }
  // },
  // created() {
  //   this.checkUserCount()
  // },
  data() {
    return {
      data: {
        labels: ['Social', 'Normal'],
        datasets: [
          {
            backgroundColor: ['#0091EA', '#F9A825'],
            data: [this.socialUser, this.normalUser]
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false
      }
    }
  }
}
</script>