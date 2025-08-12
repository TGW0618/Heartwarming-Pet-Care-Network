<template>
  <div>
    <div>
      <h1>系统用户</h1>
      <SystemUserStatistics/>
    </div>
    <div>
      <h1>订单统计</h1>
      <OrderStatistics/>
    </div>
    <div>
      <h1>收入统计</h1>
      <DataStatistics/>
    </div>
  </div>
</template>

<script setup>
import SystemUserStatistics from "@/Backend/views/Home/SystemUserStatistics.vue";
import OrderStatistics from "@/Backend/views/Home/OrderStatistics.vue";
import DataStatistics from "@/Backend/views/Home/DataStatistics.vue";
</script>


<style scoped>

</style>