<template>
  <!--  房型利用率 (条形图)-->
  <!--  数据：各房型（小型、中型、大型、豪华、VIP）的被使用次数或总入住天数。帮助优化房型配置。-->
  <div>
    <div ref="chartRef" class="chart"></div>
  </div>
</template>
<script setup>

import useChart from "@/Backend/composables/useChart.js";

const setFosterRoomTypeOption = () => ({
  title: {
    text: '房型利用率'
  },
  legend: {
    data: [
        '房型使用次数',
        '总入住天数'
    ]
  },
  tooltip: {},
  xAxis: {
    data: ['小型', '中型', '大型', '豪华', 'VIP']
  },
  yAxis: {},
  series: [{
    name: '房型使用次数',
    type: 'bar',
    data: [5, 20, 36, 10, 10],
    itemStyle: {
      color: '#ff0090'
    }
  },
    {
      name: '总入住天数',
      type: 'bar',
      data: [15, 29, 96, 20, 50],
      itemStyle: {
        color: '#8d24a9'
      }

    }
  ]
})

const {chartRef} = useChart(setFosterRoomTypeOption())


</script>

<style scoped>
.chart {
  width: 100%;
  height: 350px;
  min-width: 200px;
  max-height: 350px;
  border-radius: 15px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
  box-sizing: border-box;
}
</style>