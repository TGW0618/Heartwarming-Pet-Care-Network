<template>
  <div>
    <!--    搜索框开始-->
    <div class="search-container">
      <div class="search-bar">
        <el-input
            @keyup.enter="handleSearch"
            v-model="searchQuery"
            :prefix-icon="Search"
            class="search-input"
            type="text"
            placeholder="搜索宠物、客户或订单..."
        />
      </div>
    </div>
    <!--    搜索框结束-->

    <!-- 分类标签页 -->
    <el-tabs v-model="activeTab" class="user-tabs">
      <el-tab-pane label="医疗订单" name="veterinarian">
        <!--  就诊订单开始-->
        <div v-if="isRole === 'admin' || isRole === 'veterinarian'">
          <MedicalOrders></MedicalOrders>
        </div>
        <!--  就诊订单结束-->
      </el-tab-pane>
      <el-tab-pane label="寄养订单" name="foster_staff">
        <!--  寄养订单开始-->
        <div v-if="isRole === 'admin' || isRole === 'foster_staff'">
          <FosterOrders></FosterOrders>
        </div>
        <!--  寄养订单结束-->
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import MedicalOrders from "@/Backend/views/Order/MedicalOrders.vue";
import FosterOrders from "@/Backend/views/Order/FosterOrders.vue";
import {computed, onMounted, ref} from "vue";
import {useOrderStore} from '@/Backend/stores/order'
import {useUserStore} from "@/Backend/stores/userStore.js";
import {Search} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";


const orderStore = useOrderStore()
const userStore = useUserStore()
const isRole = computed(() => userStore.userRole)
const activeTab = ref('veterinarian')

// 页面加载时获取数据
onMounted(() => {
  orderStore.fetchAllOrders()
  userStore.getUserRole()
})

const searchQuery = ref('')

const handleSearch = () => {
  ElMessage.info(`搜索功能开发中: ${searchQuery.value}`)
}

</script>

<style scoped>

.search-bar {
  flex: 1;
  max-width: 60%;
  margin: 0 20px;
}

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
