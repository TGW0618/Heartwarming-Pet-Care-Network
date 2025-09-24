<template>
  <!--  兽医接诊量排名 (条形图)-->
  <!--  数据：各位兽医处理的订单数量。用于绩效评估和工作分配。-->
  <div>
    <div>
      <slot name="title"></slot>
    </div>
    <div ref="chartRef" class="chart"></div>
  </div>
</template>
<script setup>

import useChart from "@/Backend/composables/useChart.js";

const setOption = () => ({
  title: {
    text: '兽医接诊量排名'
  },
  tooltip: {},
  xAxis: {
    data: ['张医生', '李医生', '王医用', '赵医生', '陈医生', '黄医生', '刘医生']
  },
  yAxis: {

  },
  series: [
    {
      type: 'bar',
      data: [110, 100, 69, 101, 103, 120, 90],
      barWidth: '60%',
      itemStyle: {
        color: '#0325eb'
      }
    }
  ],
})

const { chartRef } = useChart(setOption())


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