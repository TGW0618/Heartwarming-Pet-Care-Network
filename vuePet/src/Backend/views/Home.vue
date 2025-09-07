<template>
  <el-scrollbar height="80vh">
    <div>
      <!--    系统用户-->
      <div>
        <SystemUserStatistics
            v-if="data.StatisticsData && Object.keys(data.StatisticsData).length > 0"
            :StatisticsData="data.StatisticsData"
        />
      </div>
      <!--    订单统计-->
      <div>
        <OrderStatistics
            v-if="data.StatisticsData && Object.keys(data.StatisticsData).length > 0"
            :StatisticsData="data.StatisticsData"
        />
      </div>
      <!--    收入统计-->
      <div>
        <RevenueStatistics
            v-if="data.StatisticsData && Object.keys(data.StatisticsData).length > 0"
            :StatisticsData="data.StatisticsData"
        />
      </div>
    </div>
  </el-scrollbar>
</template>


<script setup>
import SystemUserStatistics from "@/Backend/views/Home/SystemUserStatistics.vue";
import OrderStatistics from "@/Backend/views/Home/OrderStatistics.vue";
import RevenueStatistics from "@/Backend/views/Home/RevenueStatistics.vue";

import {reactive} from "vue";
import request from "@/Backend/utils/request.js";
import {ElMessage} from "element-plus";

const data = reactive({
  StatisticsData: null,
});

const getStatisticsData = async () => {
  request.get("/getStatistics/getStatisticsAll").then((res) => {
    if (res.code === 200) {
      console.log(res.data);
      if (res.data && res.data.length > 0) {
        data.StatisticsData = JSON.parse(JSON.stringify(res.data[0]));
      }
    } else {
      ElMessage.error(res.message);
    }
  });
};

getStatisticsData();


</script>


<style scoped>

</style>