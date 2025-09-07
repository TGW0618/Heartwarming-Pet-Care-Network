<template>
  <el-scrollbar height="80vh">
    <div style="height: 75vh">
      <OrdersList :order-data="data.orderData"></OrdersList>
    </div>
    <div>
      <el-pagination
          size="small"
          background
          layout="prev, pager, next"
          :total="data.orderData?.total || 0"
          :current-page="data.orderData?.pageNum || 1"
          :page-size="data.orderData?.pageSize || 5"
          @current-change="handlePageChange"
          class="mt-4"
      />
    </div>
  </el-scrollbar>

</template>

<script setup>
import {onMounted, reactive} from "vue";
import {useOrderStore} from '@/Backend/stores/order'
import OrdersList from "@/Backend/views/Order/OrdersList.vue";
import request from "@/Backend/utils/request.js";


const orderStore = useOrderStore()

// 页面加载时获取数据
onMounted(() => {
  getOrderDataList()
})


const data = reactive({
  orderData: null,
  currentPage: 1,
  pageSize: 7,
})

const getOrderDataList = async () => {
  await orderStore.getOrderAll(data.currentPage, data.pageSize)
  data.orderData = orderStore.getOrderData
}

// 处理分页变化
const handlePageChange = async (pageNum) => {
  await orderStore.getOrderAll(pageNum, data.pageSize)
  data.orderData = orderStore.getOrderData
}

/*
* 获取订单数据
* */
// const getOrderDataList = (pageNum = data.currentPage) => {
//   // 传递分页参数给后端
//   request.get(`/sysOrder/getOrderAll?pageNum=${pageNum}&pageSize=${data.pageSize}`).then(res => {
//     if (res.code === 200) {
//       data.orderData = res.data
//       data.currentPage = pageNum
//       console.log(res.data)
//     }
//   })
// }

// 处理分页变化
// const handlePageChange = (pageNum) => {
//   getOrderDataList(pageNum)
// }

</script>

<style scoped>


/* 使用 :deep() 穿透 Element Plus 组件样式 */
.search-bar :deep(.el-input__wrapper) {
  width: 100%;
  height: 40px;
  padding-left: 10px;
  border: 1px solid #e2e8f0;
  border-radius: 6px;
  font-size: 14px;
  transition: all 0.2s;
  background-color: #f8f9fa;
  box-shadow: none;
}

.search-bar :deep(.el-input__wrapper.is-focus) {
  outline: none;
  border-color: #af54c2;
  background-color: #ffffff;
  box-shadow: 0 0 0 3px #fbf6fc;
}

.search-bar :deep(.el-input__inner) {
  color: #2d3748;
}

.search-bar :deep(.el-input__inner::placeholder) {
  color: #7f8c8d;
}

</style>
