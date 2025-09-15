<template>
  <div  style="margin-bottom: 10vh">
    <!--    关键指标卡片 (KPI Cards)-->
    <div>
      <p>今日实时数据 ({{ data.overviewData?.date }})</p>
      <div>
        <el-row :gutter="15">
          <el-col :span="8">
            <RectangleComponents>
              <template #icon>
                <el-icon>
                  <UserFilled/>
                </el-icon>
              </template>
              <template #title>
                今日营收
              </template>
              <template #data>
                {{ data.overviewData?.revenueToday || 0 }}$
              </template>
            </RectangleComponents>
          </el-col>
          <el-col :span="8">
            <RectangleComponents>
              <template #icon>
                <el-icon>
                  <UserFilled/>
                </el-icon>
              </template>
              <template #title>
                今日订单
              </template>
              <template #data>
                {{ data.overviewData?.orderCountToday || 0 }}单
              </template>
            </RectangleComponents>
          </el-col>
          <el-col :span="8">
            <RectangleComponents>
              <template #icon>
                <el-icon>
                  <UserFilled/>
                </el-icon>
              </template>
              <template #title>
                新增用户
              </template>
              <template #data>
                {{ data.overviewData?.newUsersToday || 0 }}个
              </template>
            </RectangleComponents>
          </el-col>
        </el-row>
      </div>
      <div style="margin-top: 15px">
        <el-row :gutter="15">
          <el-col :span="8">
            <div ref="revenueTrendsData"
                 style="width: 100%;height: 230px;background-color: white;border-radius: 15px"></div>
          </el-col>
          <el-col :span="8">
            <div ref="orderTrendsData"
                 style="width: 100%;height: 230px;background-color: white;border-radius: 15px"></div>
          </el-col>
          <el-col :span="8">
            <div ref="serviceTrendsData"
                 style="width: 100%;height: 230px;background-color: white;border-radius: 15px"></div>
          </el-col>
        </el-row>
      </div>
    </div>
    <div>
      <p>累计数据</p>
      <div>
        <el-row :gutter="15">
          <el-col :span="6">
            <RectangleComponents>
              <template #icon>
                <el-icon>
                  <UserFilled/>
                </el-icon>
              </template>
              <template #title>
                总营收
              </template>
              <template #data>
                {{ data.AccumulateData?.revenue || 0 }}$
              </template>
            </RectangleComponents>
          </el-col>
          <el-col :span="6">
            <RectangleComponents>
              <template #icon>
                <el-icon>
                  <UserFilled/>
                </el-icon>
              </template>
              <template #title>
                总订单
              </template>
              <template #data>
                {{ data.AccumulateData?.orderCount || 0 }}单
              </template>
            </RectangleComponents>
          </el-col>
          <el-col :span="6">
            <RectangleComponents>
              <template #icon>
                <el-icon>
                  <UserFilled/>
                </el-icon>
              </template>
              <template #title>
                总用户
              </template>
              <template #data>
                {{ data.AccumulateData?.userCount || 0 }}个
              </template>
            </RectangleComponents>
          </el-col>
          <el-col :span="6">
            <RectangleComponents>
              <template #icon>
                <el-icon>
                  <UserFilled/>
                </el-icon>
              </template>
              <template #title>
                宠物总数
              </template>
              <template #data>
                {{ data.AccumulateData?.petCount || 0 }}只
              </template>
            </RectangleComponents>
          </el-col>
        </el-row>
      </div>
    </div>

  </div>
</template>

<script setup>
import * as echarts from 'echarts';
import {ref, onMounted, reactive, nextTick, watch, onUnmounted} from 'vue';
import request from "@/Backend/utils/request.js";
import {UserFilled} from "@element-plus/icons-vue";
import RectangleComponents from "@/Backend/components/DataStatistics/RectangleComponents.vue";

const data = reactive({
  overviewData: null,
  AccumulateData: null,
});

const revenueTrendsData = ref(null);
const orderTrendsData = ref(null);
const serviceTrendsData = ref(null);

// 为每个图表创建独立的实例变量
let revenueChart = null;
let orderChart = null;
let serviceChart = null;

const getOptionRevenue = () => {
  return {
    title: {
      text: '营收趋势',
      left: 'center',
      textStyle: {
        fontSize: 14,
        fontWeight: 'normal'
      }
    },
    xAxis: {
      type: 'category',
      data: ['昨日营收', '今日营收'],
    },
    yAxis: {
      type: 'value'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    series: [
      {
        data: [data.overviewData?.revenueYesterday || 0, data.overviewData?.revenueToday || 0],
        type: 'line',
        smooth: true,
        symbolSize: 6,
        lineStyle: {
          width: 2
        }
      }
    ],
  };
};

const getOptionOrder = () => {
  return {
    title: {
      text: '订单趋势',
      left: 'center',
      textStyle: {
        fontSize: 14,
        fontWeight: 'normal'
      }
    },
    xAxis: {
      type: 'category',
      data: ['昨日订单', '今日订单'],
    },
    yAxis: {
      type: 'value'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    series: [
      {
        data: [data.overviewData?.orderCountYesterday || 0, data.overviewData?.orderCountToday || 0],
        type: 'line',
        smooth: true,
        symbolSize: 6,
        lineStyle: {
          width: 2
        }
      }
    ]
  };
};

const getOptionService = () => {
  return {
    title: {
      text: '今日订单服务类型占比',
      left: 'center',
      textStyle: {
        fontSize: 14,
        fontWeight: 'normal'
      }
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: '今日订单服务类型占比',
        type: 'pie',
        data: [
          {
            value: data.overviewData?.medicalOrderCountToday || 0,
            name: '医疗服务'
          },
          {
            value: data.overviewData?.fosterOrderCountToday || 0,
            name: '寄养类型'
          },

        ]
      }
    ]
  }
}

onMounted(() => {
  getOverviewData();
  getAccumulateData();
});

const getOverviewData = () => {
  // 获取数据
  request.get('/getStatistics/getOverviewData').then((res) => {
    console.log(res)
    data.overviewData = res.data;

    // 数据获取成功后初始化图表
    nextTick(() => {
      initCharts();
    });
  });
};
const getAccumulateData = () => {
  request.get('/getStatistics/getAccumulateData').then((res) => {
    console.log(res)
    data.AccumulateData = res.data;
  });
}

const initCharts = () => {
  // 初始化营收趋势图
  if (revenueTrendsData.value && data.overviewData) {
    if (!revenueChart) {
      revenueChart = echarts.init(revenueTrendsData.value);
    }
    revenueChart.setOption(getOptionRevenue(), true);
  }

  // 初始化订单趋势图
  if (orderTrendsData.value && data.overviewData) {
    if (!orderChart) {
      orderChart = echarts.init(orderTrendsData.value);
    }
    orderChart.setOption(getOptionOrder(), true);
  }

  // 订单类型占比
  if (serviceTrendsData.value && data.overviewData) {
    if (!serviceChart) {
      serviceChart = echarts.init(serviceTrendsData.value);
    }
    serviceChart.setOption(getOptionService(), true);
  }
};

// 监听数据变化，更新图表
watch(() => data.overviewData, () => {
  if (data.overviewData) {
    initCharts();
  }
});

// 组件卸载时销毁图表实例
onUnmounted(() => {
  if (revenueChart) {
    revenueChart.dispose();
  }
  if (orderChart) {
    orderChart.dispose();
  }
  if (serviceChart) {
    serviceChart.dispose();
  }
});

// 处理窗口大小改变
const handleResize = () => {
  if (revenueChart) {
    revenueChart.resize();
  }
  if (orderChart) {
    orderChart.resize();
  }
  if (serviceChart) {
    serviceChart.resize();
  }
};

onMounted(() => {
  window.addEventListener('resize', handleResize);
});

onUnmounted(() => {
  window.removeEventListener('resize', handleResize);
});
</script>

<style scoped>
.box-card {
  width: 100%;
}

.box-card > div {
  width: 100%;
  height: 500px;
}
</style>
