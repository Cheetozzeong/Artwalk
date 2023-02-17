<template>
  <div style="height: 35vh">
    <b> - Monthly Route & Record Chart in ( 2023 )</b><br>
    <LineChartGenerator
        :data="data"
        :options="options"
        :chart-id="chartId"
        :dataset-id-key="datasetIdKey"
    />
  </div>
</template>

<script>

import { Line as LineChartGenerator } from 'vue-chartjs'
import {Chart as ChartJS, Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale, PointElement} from 'chart.js'
ChartJS.register(Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale, PointElement)

export default {
  name: "MonthlyChart",
  components: {
    LineChartGenerator
  },
  props: {
    chartId: {
      type: String,
      default: 'line-chart'
    },
    datasetIdKey: {
      type: String,
      default: 'label'
    },
    byMonthRoute: {
      type: Object
    },
    byMonthRecord: {
      type: Object,
    }
  },
  data() {
    return {
      data: {
        labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
        datasets: [
          {
            label: 'Record',
            borderColor: '#f87979',
            backgroundColor: '#f87979',
            borderJoinStyle: 'round',
            fill: false,
            borderWidth: 1,
            data: Object.values(this.byMonthRecord)
          },
          {
            label: 'Route',
            borderColor: '#07a794',
            backgroundColor: '#07a794',
            borderJoinStyle: 'round',
            fill: false,
            borderWidth: 1,
            data: Object.values(this.byMonthRoute)
          }
        ]
      },
      options: {
        // scales: {
        //   xAxes: [{
        //     ticks: {beginAtZero: true, min: 0},
        //     gridLines: {display: true},
        //     type: 'linear'
        //   }],
        //   yAxes: [{
        //     ticks: {beginAtZero: true, min: 0},
        //     gridLines: {display: true},
        //     type: 'linear'
        //   }],
        // },
        responsive: true,
        maintainAspectRatio: false,
      },
    }
  }
}
</script>

<style scoped>

</style>
