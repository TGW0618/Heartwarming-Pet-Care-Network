<template>
  <div>
    <div>
      <el-row :gutter="15">
        <!--    订单状态分布 (饼图)-->
        <el-col :span="8" class="chat_col">
          <div ref="orderChatPie" class="chatPie"></div>
        </el-col>
        <!--        热门服务榜（柱状图）-->
        <el-col :span="8" class="chat_col">
          <div ref="hotServiceChatBar" class="chatBar"></div>
        </el-col>
        <!--        每日订单金额-->
        <el-col :span="8" class="chat_col">
          <div ref="orderPriceChatBar" class="chatBar"></div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script setup>
import * as echarts from 'echarts'
import {ref, onMounted, reactive, nextTick, onBeforeMount, watch} from 'vue'
import request from "@/Backend/utils/request.js";

const data = reactive({
  orderData: null,
  orderStatus: null,//订单状态
  hotService: null,//热门服务
  orderAmount: null,//每日订单金额
})


const getOrderAnalysis = async () => {
  await request.get('/getStatistics/getOrderAnalysis').then((res) => {
    console.log(res.data)
    if (res.code === 200) {
      data.orderData = res.data
      data.orderStatus = res.data.orderStatus
      console.log('订单状态', data.orderStatus)
      data.hotService = res.data.hotService
      data.orderAmount = res.data.orderAmount
    }
  }).catch((err) => {
    console.error(err)
  })
}


let resizeObserver = null

// 订单状态分布
const orderChatPie = ref(null)
let orderChat = null
const orderChatOption = {
  title: {
    text: '订单状态分布',
    left: 'center',
    padding: [0, 0, 20, 0],
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    data: [],
  },
  series: [
    {
      name: '订单状态',
      type: 'pie',
      radius: ['25%', '50%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderWidth: 2,
              },
      data: [],
    }
  ]
}
watch(() => data.orderData, (newValue) => {
  if (newValue && newValue.orderStatus && Array.isArray(newValue.orderStatus)) {
    try {
      // 提取状态数据
      const legendData = newValue.orderStatus.map(item => item.status);
      const seriesData = newValue.orderStatus.map(item => ({
        value: item.count,
        name: item.status,

      }));

      // 更新配置
      orderChatOption.legend.data = legendData;
      orderChatOption.series[0].data = seriesData;

      // 如果图表已初始化，更新图表
      if (orderChat) {
        orderChat.setOption(orderChatOption, true);
      }
    } catch (error) {
      console.error('更新图表数据时出错:', error);
    }
  }
});

// 热门服务榜
const hotServiceChatBar = ref(null)
let hotServiceChat = null
const hotServiceChatOption = {
  title: {
    text: "热门服务TOP榜"
  },

  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },

  xAxis: {
    data: [],
  },
  yAxis: {
    type: 'value',
  },
  series: [
    {
      name: [],
      data: [],
      type: 'bar',
      barWidth: '50%',
      itemStyle:{
        color:'#0325eb'
      }
    }
  ]
}
watch(() => data.orderData, (newValue) => {
  if (newValue && newValue.hotService && Array.isArray(newValue.hotService)) {
    try {
      // 提取服务数据
      const yAxisData = newValue.hotService.map(item => item.serviceName);
      const seriesData = newValue.hotService.map(item => item.count);

      // 更新配置
      hotServiceChatOption.yAxis.data = yAxisData;
      hotServiceChatOption.series[0].data = seriesData;

      // 如果图表已初始化，更新图表
      if (hotServiceChat) {
        hotServiceChat.setOption(hotServiceChatOption, true);
      }
    } catch (error) {
      console.error('更新图表数据时出错:', error);
    }
  }
})

// 每日订单金额
const orderPriceChatBar = ref(null)
let orderPriceChat = null
const orderPriceChatOption = {
  title: {
    text: "日订单金额",
  },
  xAxis: {
    type: 'category',
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [],
      type: 'line',
      itemStyle:{
        color:"#0325eb"
      }
    }
  ]
}
watch(() => data.orderData, (newValue) => {
  if (newValue && newValue.orderAmount && Array.isArray(newValue.orderAmount)) {
    try {
      // 提取服务数据
      const xAxisData = newValue.orderAmount.map(item => item.orderDate);
      const seriesData = newValue.orderAmount.map(item => item.orderAmount);

      // 更新配置
      orderPriceChatOption.xAxis.data = xAxisData;
      orderPriceChatOption.series[0].data = seriesData;
      if (orderPriceChat) {
        orderPriceChat.setOption(orderPriceChatOption, true);
      }

    } catch (error) {
      console.error('更新图表数据时出错:', error);
    }
  }
})

onMounted(async () => {
  await getOrderAnalysis()
  await nextTick()
  initCharts()
  window.addEventListener('resize', handleResize)
})


const initCharts = () => {
  try {
    // 如果数据已加载，初始化时就使用实际数据
    if (data.orderData && data.orderData.orderStatus && Array.isArray(data.orderData.orderStatus)) {
      const legendData = data.orderData.orderStatus.map(item => item.status);
      const seriesData = data.orderData.orderStatus.map(item => ({
        value: item.count,
        name: item.status
      }));

      orderChatOption.legend.data = legendData;
      orderChatOption.series[0].data = seriesData;
    }
    if (orderChatPie.value) {
      orderChat = echarts.init(orderChatPie.value)
      orderChat.setOption(orderChatOption)
    }

    if (data.orderData && data.orderData.hotService && Array.isArray(data.orderData.hotService)) {
      const serviceName = data.orderData.hotService.map(item => item.serviceName);
      const seriesData = data.orderData.hotService.map(item => item.count);
      hotServiceChatOption.xAxis.data = serviceName;
      hotServiceChatOption.series[0].data = seriesData;
    }
    if (hotServiceChatBar.value) {
      hotServiceChat = echarts.init(hotServiceChatBar.value)
      hotServiceChat.setOption(hotServiceChatOption)
    }

    if (data.orderData && data.orderData.orderAmount && Array.isArray(data.orderData.orderAmount)) {
      const orderDate = data.orderData.orderAmount.map(item => item.date);
      const seriesData = data.orderData.orderAmount.map(item => item.amount);
      orderPriceChatOption.xAxis.data = orderDate;
      orderPriceChatOption.series[0].data = seriesData;
    }
    if (orderPriceChatBar.value) {
      orderPriceChat = echarts.init(orderPriceChatBar.value)
      orderPriceChat.setOption(orderPriceChatOption)
    }

  } catch (e) {
    console.log('初始化图表失败：' + e)
  }
  // 监听容器尺寸变化
  setupResizeObserver()
}
const setupResizeObserver = () => {
  if (window.ResizeObserver) {
    resizeObserver = new ResizeObserver(() => {
      handleResize()
    })

    if (orderChatPie.value) resizeObserver.observe(orderChatPie.value)
    if (hotServiceChatBar.value) resizeObserver.observe(hotServiceChatBar.value)
    if (orderPriceChatBar.value) resizeObserver.observe(orderPriceChatBar.value)
  }
}


const handleResize = () => {
  if (orderChat) {
    orderChat.resize()
  }
  if (hotServiceChat) {
    hotServiceChat.resize()
  }
  if (orderPriceChat) {
    orderPriceChat.resize()
  }
}

onBeforeMount(() => {
  if (orderChat) {
    orderChat.dispose()
  }
  if (hotServiceChat) {
    hotServiceChat.dispose()
  }
  if (orderPriceChat) {
    orderPriceChat.dispose()
  }
  window.removeEventListener('resize', handleResize)
})


</script>

<style scoped>
.chat_col {
  padding: 10px;
  box-sizing: border-box;
}

.chatPie, .chatBar {
  width: 100%;
  height: 350px;
  min-width: 200px;
  max-height: 350px;
  border-radius: 15px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
  box-sizing: border-box; /* 确保边框和阴影不影响尺寸 */
}


</style>