<template>
  <!--  常见病症统计 (词云或条形图)-->
  <!--  数据：从medical_record的diagnosis（诊断结果）或symptoms（症状）字段中提取高频关键词。了解主流需求，针对性采购药品或培训。-->
  <!-- -->
  <div>
    <div>
      <slot name="title"></slot>
    </div>
    <div ref="chartRef" class="chart"></div>
  </div>
</template>
<script setup>

import useChart from "@/Backend/composables/useChart.js";

const {chartRef} = useChart({
  title: {
    text: '常见病症统计',
    textStyle: {
      color: '#333',
      fontSize: 16
    }
  },
  legend: {
    data: ['疫苗', '体检', '绝育']
  },
  tooltip: {},
  series: [
    {
      type: 'pie',
      data: [
        {
          value: 1018,
          name: '疫苗',
          itemStyle: {
            color: '#0325eb'
          }
        },
        {
          value: 1735,
          name: '体检',
          itemStyle: {
            color: '#ffa500'
          }
        },
        {
          value: 580,
          name: '绝育',
          itemStyle: {
            color: '#ffd700'
          }
        }
      ]
    }
  ]
})

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