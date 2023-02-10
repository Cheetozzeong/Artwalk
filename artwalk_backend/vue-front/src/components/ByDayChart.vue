<template>
  <div style="height: 35vh">
    <b> - By Day Route & Record Accumulate Chart in ( 2023 )</b><br>
    <LineChartGenerator
        :data="data"
        :options="options"
        :chart-id="chartId"
        :dataset-id-key="datasetIdKey"
    />
  </div>
</template>

<script>

import {Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale} from 'chart.js'
import {Line as LineChartGenerator} from "vue-chartjs";
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

export default {
  name: 'ByDayChart.vue',
  components: {
    LineChartGenerator
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
    byDayRoute: {
      type: Object,
    },
    byDayRecord: {
      type: Object,
    },
  },
  data() {
    return {
      data: {
        labels: Object.keys(this.byDayRoute),
        datasets: [
            {
              label: 'Record',
              backgroundColor: '#f87979',
              borderColor: '#f87979',
              borderJoinStyle: 'round',
              fill: false,
              borderWidth: 1,
              data: Object.values(this.byDayRecord),
            },
            {
              label: 'Route',
              borderColor: '#07a794',
              backgroundColor: '#07a794',
              borderJoinStyle: 'round',
              fill: false,
              borderWidth: 1,
              data: Object.values(this.byDayRoute),
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
      }
    }
  }
}
</script>