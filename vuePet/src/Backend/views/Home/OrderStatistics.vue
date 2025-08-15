<!--订单统计-->
<template>
  <div>
    <div ref="chartRef" class="option" style="width: 100%; height: 300px">
      <!-- 图表容器 -->
    </div>
  </div>
</template>l

<script setup>
import * as echarts from 'echarts';
import {onBeforeUnmount, onMounted, ref} from 'vue';

const props = defineProps({
  StatisticsData: {
    type: Object,
    required: true,
  },
});


const chartRef = ref(null);
let myChart = null;

const option = {
  title: {
    text: '订单统计',
    subtext: '总订单：' + props.StatisticsData.totalOrder,
    left: 'left'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  color: [
    '#ffea00',
    '#951515',
  ],
  series: [
    {
      name: '订单',
      type: 'pie',
      radius: ' 85%',
      center: ['50%', '50%'],
      roseType: 'area',
      itemStyle: {
        borderRadius: 8
      },
      data: [
        {value: props.StatisticsData.totalMedicalOrders, name: '医疗订单'},
        {value: props.StatisticsData.totalFosterOrders, name: '寄养订单'},
      ],
    }
  ]
};

// 在组件挂载后初始化图表
onMounted(() => {
  // 确保 DOM 渲染完成
  setTimeout(() => {
    if (chartRef.value) {
      try {
        // 销毁之前的实例（如果有）
        if (myChart) {
          myChart.dispose();
        }

        // 初始化图表
        myChart = echarts.init(chartRef.value);
        option && myChart.setOption(option);

      } catch (error) {
      }
    } else {
    }
  }, 0);
});

// 窗口大小改变时重置图表大小
const handleResize = () => {
  if (myChart) {
    myChart.resize();
  }
};

// 添加窗口大小监听
onMounted(() => {
  window.addEventListener('resize', handleResize);
});

// 组件卸载前销毁图表实例
onBeforeUnmount(() => {
  if (myChart) {
    myChart.dispose();
    myChart = null;
  }
  window.removeEventListener('resize', handleResize);
});
</script>

<style scoped>
.DataStatistics {
  padding: 20px;
  background-color: #ffffff;
  margin-top: 15px;
  margin-bottom: 15px;
  border-radius: 8px;
}

.option {
  padding: 20px;
  background-color: #ffffff;
  border-radius: 8px;
  margin-bottom: 15px;
  box-sizing: border-box;
}
</style>
