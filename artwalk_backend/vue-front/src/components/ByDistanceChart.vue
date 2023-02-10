<template>
  <div style="height: 35vh">
    <b> - By Distance Route & Record Chart</b><br>
    <Bar
        :data="data"
        :options="options"
        :chart-id="chartId"
        :dataset-id-key="datasetIdKey"
    />
  </div>
</template>

<script>

import {Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale} from 'chart.js'
import { Bar } from 'vue-chartjs'
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

export default {
  name: "ByDistanceChart",
  components: {
    Bar
  },
  props: {
    chartId: {
      type: String,
      default: 'bar-chart'
    },
    datasetIdKey: {
      type: String,
      default: 'label'
    },
    routeDistance: {
      type: Object,
    },
    recordDistance: {
      type: Object,
    },
  },
  data() {
    return {
      data: {
        labels: ['0 - 3km', '3 - 10km', '10 - 30km', '30 - 50km', '50 - 100km', '100 - 300km', '300km 이상'],
        datasets: [
          {
            label: 'Record',
            backgroundColor: '#f87979',
            data: Object.values(this.recordDistance)
          },
          {
            label: 'Route',
            backgroundColor: '#07a794',
            data: Object.values(this.routeDistance)
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
      }
    }
  }
}
</script>

<style scoped>

</style>