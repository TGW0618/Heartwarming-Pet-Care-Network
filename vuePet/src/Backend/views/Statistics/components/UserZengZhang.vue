<template>
  <!--  用户增长趋势 (折线图)
        数据：每日/每周新增用户数，观察推广活动效果。-->
  <div>
    <div>
      <slot name="title">

      </slot>
    </div>
    <div ref="chartRef" class="userZengZhang">
    </div>
  </div>
</template>

<script setup>

import useChart from "@/Backend/composables/useChart.js";


const setUserZengZhang = () => {
  return {
    title: {
      text:'用户增长趋势',
      left: 'center',
      textStyle: {
        color: '#333',
        fontSize: 16
      }
    },
    tooltip: {
      trigger: 'axis'
    },

    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '每日新增用户数',
        type: 'line',
        data: [120, 200, 150, 80, 70, 110, 130],
        smooth: true
      }, {
        name: '每周新增用户数',
        type: 'line',
        data: [1120, 2100, 1150, 810, 710, 1110, 1310],
        smooth: true
      }
    ]
  }
}

const {chartRef} = useChart(setUserZengZhang)


</script>

<style scoped>
.userZengZhang {
  width: 100%;
  height: 350px;
  min-width: 200px;
  max-height: 350px;
  border-radius: 15px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
  box-sizing: border-box;
}
</style>
