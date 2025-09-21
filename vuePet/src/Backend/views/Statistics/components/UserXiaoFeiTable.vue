<template>
  <!--    用户消费排名 (表格)
    数据：消费金额最高的前N位用户（可关联其宠物信息），用于识别高价值客户。-->
  <div>
    <div>
      <slot name="title"></slot>
    </div>
    <div ref="chartRef" class="char"></div>
  </div>

</template>
<script setup>
import useChart from '../../../composables/useChart'




const setUserXiaoFeiOption = () => ({
  title: {
    text: '用户消费排名',
    left: 'center',
    textStyle: {
      color: '#333',
      fontSize: 16
    },
    subtext: '最近30天',
    subtextStyle: {
      color: '#666',
      fontSize: 12
    },
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
    boundaryGap: false,
    data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '用户消费金额',
      type: 'line',
      data: [1200, 132, 101, 134, 90, 230, 210, 120, 132, 101, 134, 90]
    }
  ]

})

const {chartRef} = useChart(setUserXiaoFeiOption)

</script>

<style scoped>
.char {
  width: 100%;
  height: 350px;
  min-width: 200px;
  max-height: 350px;
  border-radius: 15px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
  box-sizing: border-box; /* 确保边框和阴影不影响尺寸 */
}
</style>