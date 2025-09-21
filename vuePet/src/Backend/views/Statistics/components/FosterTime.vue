<template>
  <!--  寄养时长分布 (饼图)-->
  <!--  数据：短期（1-3天）、中期（4-7天）、长期（7天以上）的订单占比。有助于制定价格策略。-->
  <div>
    <div ref="chartRef" class="chart"></div>
  </div>

</template>
<script setup>
import useChart from "@/Backend/composables/useChart.js";

const setFosterTimeOption = () => ({
  title: {
    text: '寄养时长分布'
  },
  tooltip: {},
  legend: {
    data: ['短期', '中期', '长期']
  },
  series: [
    {
      type: 'pie',
      data: [
        {
          value: 1048,
          name: '短期',
          itemStyle: {
            color: '#FFA500'
          }
        },
        {
          value: 735,
          name: '中期',
          itemStyle: {
            color: '#FFD700'
          }
        },
        {
          value: 580,
          name: '长期',
          itemStyle: {
            color: 'rgb(65,91,255)'
          }
        }
      ]
    }
  ]
})

const {chartRef} = useChart(setFosterTimeOption())



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